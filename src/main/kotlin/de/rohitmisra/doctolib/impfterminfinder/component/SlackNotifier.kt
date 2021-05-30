package de.rohitmisra.doctolib.impfterminfinder.component

import com.slack.api.Slack
import com.slack.api.methods.MethodsClient
import com.slack.api.methods.request.chat.ChatPostMessageRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.Properties
import javax.annotation.PostConstruct


@Component
class SlackNotifier(
    @Autowired
    val slackBotProperties: SlackBotProperties
) : Notifier {

    var slack: Slack = Slack.getInstance()
    private var botName = ""
    private var token = ""
    private var channel = ""

    @PostConstruct
    fun setup() {
        botName = slackBotProperties.botConfig["bot-name"].toString()
        token = slackBotProperties.botConfig["auth-token"].toString()
        channel = slackBotProperties.botConfig["channel-name"].toString()
    }

    override fun notify(message: String) {
        val methods: MethodsClient = slack.methods(token)
        val request = ChatPostMessageRequest.builder()
            .channel(channel)
            .text(message)
            .build()
        methods.chatPostMessage(request)
    }

}