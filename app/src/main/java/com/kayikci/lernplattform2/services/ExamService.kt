package com.kayikci.lernplattform2.services

import com.kayikci.lernplattform2.models.Exam
import retrofit2.Call
import retrofit2.http.*
import java.util.*


interface ExamService {


    @GET("exam")
    fun getExamListByUser(): Call<List<Exam>>

    @POST("exam")
    fun addExam(@Body newExam: Exam): Call<Exam>

    @GET("exam/{id}")
    fun getExam(@Path("id") id: Long): Call<Exam>


    @PUT("exam/{id}")
    fun updateExam(@Path("id") id: Long, @Body exam: Exam): Call<Exam>


    @DELETE("exam/{id}")
    fun deleteExam(@Path("id") id: Long): Call<Unit>
}




