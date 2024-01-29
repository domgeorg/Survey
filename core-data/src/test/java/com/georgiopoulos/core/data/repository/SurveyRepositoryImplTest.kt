package com.georgiopoulos.core.data.repository

import com.georgiopoulos.core.data.model.answer.AnswerRequestBuilder
import com.georgiopoulos.core.data.model.question.QuestionDataModel
import com.georgiopoulos.core.data.model.question.mapper.QuestionMapper
import com.georgiopoulos.core.network.api.SurveyApi
import com.georgiopoulos.core.network.request.SubmitAnswerRequest
import com.georgiopoulos.core.network.response.QuestionResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

@ExperimentalCoroutinesApi
class SurveyRepositoryImplTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private val api = mockk<SurveyApi>()
    private val questionMapper = mockk<QuestionMapper>()
    private val answerRequestBuilder = mockk<AnswerRequestBuilder>()

    private lateinit var surveyRepositoryImpl: SurveyRepositoryImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        surveyRepositoryImpl = SurveyRepositoryImpl(
            api = api,
            questionMapper = questionMapper,
            answerRequestBuilder = answerRequestBuilder,
            ioDispatcher = testDispatcher,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given valid response from API, When getQuestions, Then return mapped question data model list`() = runTest {
        // Given
        val questionResponses = listOf(QuestionResponse(1, "What is your favorite colour?"))
        val questionDataModels = listOf(QuestionDataModel(1, "What is your favorite colour?"))

        coEvery { api.getQuestions() } returns questionResponses
        coEvery { questionMapper.map(questionResponses) } returns questionDataModels

        // When
        val result = surveyRepositoryImpl.getQuestions()

        // Then
        Assert.assertEquals(success(questionDataModels), result)
    }

    @Test
    fun `Given exception from API, When getQuestions, Then return failure result`() = runTest {
        // Given
        val exception = RuntimeException("API Error")
        coEvery { api.getQuestions() } throws exception

        // When
        val result = surveyRepositoryImpl.getQuestions()

        // Then
        Assert.assertEquals(failure<List<QuestionDataModel>>(exception), result)
    }

    @Test
    fun `Given valid parameters, When submitAnswer, Then return success result`() = runTest {
        // Given
        val questionId = 1
        val answer = "Mock Answer"
        val request = SubmitAnswerRequest(questionId, answer)

        coEvery { answerRequestBuilder.build(questionId, answer) } returns request
        coEvery { api.postAnswer(request) } returns Unit

        // When
        val result = surveyRepositoryImpl.submitAnswer(questionId, answer)

        // Then
        Assert.assertEquals(success(Unit), result)
    }

    @Test
    fun `Given exception, When submitAnswer, Then return failure result`() = runTest {
        // Given
        val questionId = 1
        val answer = "Mock Answer"
        val exception = RuntimeException("API Error")

        coEvery { answerRequestBuilder.build(questionId, answer) } throws exception

        // When
        val result = surveyRepositoryImpl.submitAnswer(questionId, answer)

        // Then
        Assert.assertEquals(failure<Unit>(exception), result)
    }
}
