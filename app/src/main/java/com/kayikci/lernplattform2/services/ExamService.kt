package com.kayikci.lernplattform2.services

import com.kayikci.lernplattform2.models.Exam
import retrofit2.Call
import retrofit2.http.*
import java.util.*


interface ExamService {



    @GET("exam")
    fun getExamList(): Call<List<Exam>>

    @GET("exam/{examId}")
    fun getExam(@Path("examId") examId: Int): Call<Exam>

    @POST("exam")
    fun addExam(@Body newExam: Exam): Call<Exam>


    @PUT("exam/{examId}")
    fun updateExam(@Path("examId") examId: Int, @Body exam: Exam): Call<Exam>



    @DELETE("exam/{examId}")
    fun deleteExam(@Path("examId") examId: Int): Call<Unit>
}




