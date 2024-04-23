package com.kayikci.lernplattform2.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


@Parcelize
data class Exam(
    var id: Long? = 0,
    var pruefungsName: String? = "",
    var info: String? = "",
    var beschreibung: String? = "",
    var erstellDatum: ZonedDateTime? = null,
    var aenderungsDatum: ZonedDateTime? = null,
    var anzahlFragen: Int? = 0,
    var isSelected: Boolean = false
) : Parcelable {
    fun toPdfString(): String {
        val germanErstelldatum: ZonedDateTime? = erstellDatum?.withZoneSameInstant(
            ZoneId.of("Europe/Berlin")
        )
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        val formattedErstelldatum: String? = germanErstelldatum?.format(formatter)

        return "Name der Prüfung: $pruefungsName\n" +
                "Weitere Informationen: $info\n" +
                "Beschreibung der Prüfung: $beschreibung\n" +
                "Erstellt am: $formattedErstelldatum"

    }
}

