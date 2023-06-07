package com.kayikci.lernplattform2.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Exam(
    var id: Long? = 0,
    var pruefungsName: String? = "",
    var info: String? = "",
    var beschreibung: String? = "",
    var erstellDatum: String? = "",
    var aenderungsDatum: String? = "",
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

