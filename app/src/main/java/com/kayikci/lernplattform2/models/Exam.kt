package com.kayikci.lernplattform2.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.ZoneOffset
import java.time.ZonedDateTime


@Parcelize
data class Exam(
    var id: Long? = 0,
    var pruefungsName: String? = "",
    var info: String? = "",
    var beschreibung: String? = "",
    var erstellDatum: ZonedDateTime? = ZonedDateTime.now(ZoneOffset.UTC),
    var aenderungsDatum: ZonedDateTime = ZonedDateTime.now(ZoneOffset.UTC),
    var anzahlFragen: Int? = 0,
    var isSelected: Boolean = false
) : Parcelable {
    fun toPdfString(): String {
        return "Name der Prüfung: $pruefungsName\n" +
                "Weitere Informationen: $info\n" +
                "Beschreibung der Prüfung: $beschreibung\n" +
                "Erstellt am: $erstellDatum"

    }
}

