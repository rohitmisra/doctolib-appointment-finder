package de.rohitmisra.doctolib.impfterminfinder.model.noAvailability

data class NoAvailability(
    val availabilities: List<Any>,
    val message: String,
    val reason: String,
    val total: Int
)