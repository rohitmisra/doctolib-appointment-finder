package de.rohitmisra.doctolib.impfterminfinder.component

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import de.rohitmisra.doctolib.impfterminfinder.model.Praxis
import de.rohitmisra.doctolib.impfterminfinder.model.availability.Availability
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.annotation.PostConstruct


@Component
@EnableScheduling
class Finder(
    @Autowired
    private val notifier: Notifier,
    @Autowired
    private val finderProperties: FinderProperties
) {
    private val log = LoggerFactory.getLogger(Finder::class.java)

    private val objectMapper = jacksonObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
    private val client = OkHttpClient()

    private val praxisList = mutableListOf<Praxis>()

    @PostConstruct
    fun setup() {
        finderProperties.praxis.map {
            praxisList.add(
                objectMapper.readValue(
                    objectMapper.writeValueAsString(it), Praxis::class.java
                )
            )
        }
    }

    @Scheduled(fixedDelay = 30 * 1000)
    fun scheduleFixedDelayTask() {
        log.info("job triggered, looking for appointments:")
        try {
            praxisList.map {
                findSlots(
                    "https://www.doctolib.de/availabilities.json?start_date=2021-05-29" +
                            "&visit_motive_ids=${it.visitMotiveIds}" +
                            "&agenda_ids=${it.agendaIds}" +
                            "&insurance_sector=public&practice_ids=${it.practiceIds}" +
                            "&limit=50",
                    it
                )
            }
        } catch (ex: Exception) {
            log.error("unexpected exception while running job: ", ex)
        }

    }

    fun findSlots(url: String, practice: Praxis) {
        log.info("Finding slots for $practice.")
        val request = Request.Builder()
            .url(url)
            .build()
        val body = client.newCall(request).execute().body().string()
        log.info("got appointments for ${practice.tag}")
        try {
            val availability = objectMapper.readValue(body, Availability::class.java)
            availability.availabilities.takeIf {
                it.isNotEmpty()
            }?.firstOrNull { availabilityX ->
                availabilityX?.slots?.isNotEmpty() ?: false
            }?.slots?.first()?.let { slot ->
                log.info("found a slot for ${practice.tag}: $slot")
                ZonedDateTime.parse(slot.toString())
                    .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)).let {
                        notifier.notify(practice.toBeautifiedMessage(it))
                    }
            }
        } catch (ex: Exception) {
            log.error("call failed for $url", ex)
        }
    }
}