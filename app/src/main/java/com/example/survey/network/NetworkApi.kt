package com.example.survey.network

import com.example.survey.data.SubmitData
import com.example.survey.data.SurveyData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NetworkApi {

    @GET("/questions")
    suspend fun getSurveyData(): List<SurveyData>

    @POST("/question/submit")
    suspend fun postAnswer(@Body data: SubmitData)
}