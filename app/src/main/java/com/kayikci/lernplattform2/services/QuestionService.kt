package com.kayikci.lernplattform2.services

import com.kayikci.lernplattform2.models.Exam
import com.kayikci.lernplattform2.models.Question
import retrofit2.Call
import retrofit2.http.*

interface QuestionService {

    @GET("/exam/{examId}/questions")
    fun getQuestionList(): Call<List<Question>>

    @GET("/exam/{examId}/questions/{questionId}")
    fun getQuestion(@Path("examId") examId: Int, @Path("questionId") questionId: Int): Call<Question>


    @POST("/exam/{examId}/questions")
    fun addQuestion(@Path("examId") examId: Int, @Body newQuestion: Question): Call<Question>


    @PUT("/exam/{examId}/questions/{questionId}")
    fun updateQuestion(@Path("examId") examId: Int, @Path("questionId") questionId: Int, @Body question: Question): Call<Question>



    @DELETE("/exam/{examId}/questions/{questionId}")
    fun deleteQuestion(@Path("examId") examId: Int, @Path("questionId") questionId: Int): Call<Unit>
}