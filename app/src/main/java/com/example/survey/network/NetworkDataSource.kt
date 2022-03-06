package com.example.survey.network

import com.example.survey.data.SubmitData
import com.example.survey.data.SurveyData
import javax.inject.Inject

class NetworkDataSource @Inject constructor(private val networkApi: NetworkApi) {

    suspend fun getSurveyData(): Result<List<SurveyData>> = try {
        Result.success(networkApi.getSurveyData())
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun submitAnswer(data: SubmitData): Result<Unit> = try {
        Result.success(networkApi.postAnswer(data))
    } catch (e: Exception) {
        Result.failure(e)
    }
}