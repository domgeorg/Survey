package com.georgiopoulos.core.data.repository

import com.georgiopoulos.core.data.AppDispatchers
import com.georgiopoulos.core.data.Dispatcher
import com.georgiopoulos.core.data.model.answer.AnswerRequestBuilder
import com.georgiopoulos.core.data.model.question.QuestionDataModel
import com.georgiopoulos.core.data.model.question.mapper.QuestionMapper
import com.georgiopoulos.core.network.api.SurveyApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SurveyRepositoryImpl @Inject constructor(
    private val api: SurveyApi,
    private val questionMapper: QuestionMapper,
    private val answerRequestBuilder: AnswerRequestBuilder,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : SurveyRepository {
    override suspend fun getQuestions(): Result<List<QuestionDataModel>> =
        withContext(ioDispatcher) {
            try {
                val response = api.getQuestions()
                Result.success(questionMapper.map(response))
            } catch (exception: Exception) {
                Result.failure(exception)
            }
        }

    override suspend fun submitAnswer(questionId: Int, answer: String): Result<Unit> =
        withContext(ioDispatcher) {
            try {
                val request = answerRequestBuilder.build(questionId, answer)
                Result.success(api.postAnswer(request))
            } catch (exception: Exception) {
                Result.failure(exception)
            }
        }
}