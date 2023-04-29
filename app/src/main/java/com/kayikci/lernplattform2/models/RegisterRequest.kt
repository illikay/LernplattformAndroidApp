package com.kayikci.lernplattform2.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterRequest(
    private val firstname: String? = "",
    private val lastname: String? = "",
    private val email: String? = "",
    private val password: String? = ""
) : Parcelable

