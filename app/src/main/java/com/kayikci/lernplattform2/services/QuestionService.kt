package com.kayikci.lernplattform2.services

import com.kayikci.lernplattform2.models.Question
import retrofit2.Response
import retrofit2.http.*

interface QuestionService {

    @GET("/exam/{examId}/questions")
    suspend fun getQuestionList(@Path("examId") examId: Long): Response<List<Question>>

    @GET("/exam/{examId}/questions/{questionId}")
    suspend fun getQuestion(
        @Path("examId") examId: Long,
        @Path("questionId") questionId: Long
    ): Response<Question>


    @POST("/exam/{examId}/questions")
    suspend fun addQuestion(@Path("examId") examId: Long, @Body newQuestion: Question): Response<Question>


    @PUT("/exam/{examId}/questions/{questionId}")
    suspend fun updateQuestion(
        @Path("examId") examId: Long,
        @Path("questionId") questionId: Long,
        @Body question: Question
    ): Response<Question>


    @DELETE("/exam/{examId}/questions/{questionId}")
    suspend fun deleteQuestion(
        @Path("examId") examId: Long,
        @Path("questionId") questionId: Long
    ): Response<Unit>
}