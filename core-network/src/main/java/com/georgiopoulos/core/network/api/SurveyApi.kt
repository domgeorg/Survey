package com.georgiopoulos.core.network.api

import com.georgiopoulos.core.network.request.SubmitAnswerRequest
import com.georgiopoulos.core.network.response.QuestionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SurveyApi {

    @GET("/questions")
    suspend fun getQuestions(): List<QuestionResponse>

    @POST("/question/submit")
    suspend fun postAnswer(@Body request: SubmitAnswerRequest)
}