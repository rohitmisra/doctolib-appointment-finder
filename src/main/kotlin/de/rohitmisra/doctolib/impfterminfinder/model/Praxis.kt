package de.rohitmisra.doctolib.impfterminfinder.model

data class Praxis (
    val tag: String,
    val visitMotiveIds: String,
    val agendaIds: String,
    val practiceIds: String,
    val path: String,
    val message: String
) {
    fun toBeautifiedMessage(time: String): String {
        return "Appointment found for $message at: $time. \nhttps://www.doctolib.de/$path"
    }
}