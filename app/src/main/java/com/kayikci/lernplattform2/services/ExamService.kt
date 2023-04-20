package com.kayikci.lernplattform2.services

import com.kayikci.lernplattform2.models.Exam
import retrofit2.Response
import retrofit2.http.*
import java.util.*


interface ExamService {


    @GET("exam")
    suspend fun getExamListByUser(): Response<List<Exam>>

    @POST("exam")
    suspend fun addExam(@Body newExam: Exam): Response<Exam>

    @GET("exam/{id}")
    suspend fun getExam(@Path("id") id: Long): Response<Exam>


    @PUT("exam/{id}")
    suspend fun updateExam(@Path("id") id: Long, @Body exam: Exam): Response<Exam>


    @DELETE("exam/{id}")
    suspend fun deleteExam(@Path("id") id: Long): Response<Unit>
}




