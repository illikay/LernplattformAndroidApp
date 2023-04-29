package com.kayikci.lernplattform2.services

import com.kayikci.lernplattform2.models.LoginRequest
import com.kayikci.lernplattform2.models.LoginResponse
import com.kayikci.lernplattform2.models.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("usermanagement/authenticate")
    suspend fun authenticate(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("usermanagement/logout")
    suspend fun logout(@Body loginRequest: LoginRequest): Response<Unit>

    @POST("usermanagement/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<LoginResponse>
}