package com.georgiopoulos.core.data.repository

import com.georgiopoulos.core.data.model.question.QuestionDataModel

interface SurveyRepository {

    suspend fun getQuestions(): Result<List<QuestionDataModel>>

    suspend fun submitAnswer(questionId: Int, answer: String): Result<Unit>
}