package com.kayikci.lernplattform2.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Exam(

	var id: Int = 0,
	var pruefungsName: String = "",
	var info: String = "",
	var beschreibung: String = "",
	var erstellDatum: String = "",
	var aenderungsDatum: String = "",
	var anzahlFragen: Int  = 0,
	var questions: ArrayList<Question> = ArrayList<Question>()
) : Parcelable

