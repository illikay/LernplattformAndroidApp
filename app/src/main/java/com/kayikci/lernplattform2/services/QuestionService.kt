package com.kayikci.lernplattform2.services

import com.kayikci.lernplattform2.models.Exam
import com.kayikci.lernplattform2.models.Question
import retrofit2.Call
import retrofit2.http.*

interface QuestionService {

    @GET("question")
    fun getQuestionList(): Call<List<Exam>>

    @GET("question/{id}")
    fun getQuestion(@Path("id") id: Int): Call<Question>

    @POST("question")
    fun addQuestion(@Body newQuestion: Question): Call<Question>


    @PUT("question/{id}")
    fun updateQuestion(@Path("id") id: Int, @Body question: Question): Call<Question>



    @DELETE("question/{id}")
    fun deleteQuestion(@Path("id") id: Int): Call<Unit>
}