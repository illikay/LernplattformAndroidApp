package com.kayikci.lernplattform2.services

import com.kayikci.lernplattform2.models.Exam
import com.kayikci.lernplattform2.models.Question
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @GET("/user/{userId}/exams")
    fun getExamListByUser(@Path("userId") userId: Long?): Call<List<Exam>>

    @POST("/user/{userId}/exams")
    fun addQuestion(@Path("id") userId: Long?, @Body newExam: Exam): Call<Exam>




}