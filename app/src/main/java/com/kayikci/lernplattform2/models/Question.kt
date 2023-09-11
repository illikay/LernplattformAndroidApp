package com.kayikci.lernplattform2.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.ZoneOffset
import java.time.ZonedDateTime


@Parcelize
data class Question(

    var id: Long? = 0,
    var questionFrage: String? = "",
    var questionHinweis: String? = "",
    var questionLoesung: String? = "",
    var erstellDatum: ZonedDateTime? = ZonedDateTime.now(ZoneOffset.UTC),
    var aenderungsDatum: ZonedDateTime? = ZonedDateTime.now(ZoneOffset.UTC),
    var isBeantwortet: Boolean? = false,
    var exam: Exam? = null
) : Parcelable {
    fun toPdfString(index: Int): String {
        return "$index. Aufgabe: $questionFrage\n" +
                "Aufgabenhinweis: $questionHinweis\n" +
                "Musterlösung: $questionLoesung"
    }
}










