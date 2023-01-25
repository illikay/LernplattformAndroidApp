package com.kayikci.lernplattform2.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Question(

    var id: Int = 0,
    var questionFrage: String = "",
    var questionHinweis: String = "",
    var questionLoesung: String = "",
    var erstellDatum: String = "",
    var aenderungsDatum: String = "",
    var isBeantwortet: Boolean = false,
    var exam:Exam = Exam()
    ): Parcelable










