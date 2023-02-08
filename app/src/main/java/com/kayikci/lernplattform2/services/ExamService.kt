package com.kayikci.lernplattform2.services

import com.kayikci.lernplattform2.models.Exam
import retrofit2.Call
import retrofit2.http.*
import java.util.*


interface ExamService {



    @GET("exam")
    fun getExamList(): Call<List<Exam>>

    @GET("exam/{id}")
    fun getExam(@Path("id") id: Int): Call<Exam>

    @POST("exam")
    fun addExam(@Body newExam: Exam): Call<Exam>


    @PUT("exam/{id}")
    fun updateExam(@Path("id") id: Int, @Body exam: Exam): Call<Exam>



    @DELETE("exam/{id}")
    fun deleteExam(@Path("id") id: Int): Call<Unit>
}




