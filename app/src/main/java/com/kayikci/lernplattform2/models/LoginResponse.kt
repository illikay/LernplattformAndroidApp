package com.kayikci.lernplattform2.models

data class LoginResponse(
    val userId: Long,
    val token: String
)