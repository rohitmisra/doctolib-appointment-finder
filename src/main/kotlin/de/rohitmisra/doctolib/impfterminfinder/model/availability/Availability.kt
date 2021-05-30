package de.rohitmisra.doctolib.impfterminfinder.model.availability

data class Availability(
    val availabilities: List<AvailabilityX?>,
    val next_slot: String?,
    val search_result: SearchResult?,
    val total: Int?
)