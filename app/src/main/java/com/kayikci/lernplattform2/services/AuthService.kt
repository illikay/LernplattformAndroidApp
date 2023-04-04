package com.kayikci.lernplattform2.services

import com.kayikci.lernplattform2.models.Exam
import com.kayikci.lernplattform2.models.LoginRequest
import com.kayikci.lernplattform2.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {
    @POST("user/authenticate")
    fun authenticate(@Body loginRequest: LoginRequest): Call<LoginResponse>
}