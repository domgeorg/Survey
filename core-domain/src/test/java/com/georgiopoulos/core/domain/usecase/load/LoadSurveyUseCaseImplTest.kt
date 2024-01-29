package com.georgiopoulos.core.domain.usecase.load

import com.georgiopoulos.core.data.model.question.QuestionDataModel
import com.georgiopoulos.core.data.repository.SurveyRepository
import com.georgiopoulos.core.domain.model.Outcome
import com.georgiopoulos.core.domain.model.error.ErrorModel
import com.georgiopoulos.core.domain.model.error.mapper.ErrorMapper
import com.georgiopoulos.core.domain.model.question.QuestionModel
import com.georgiopoulos.core.domain.model.question.mapper.QuestionMapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoadSurveyUseCaseImplTest {

    private val repository = mockk<SurveyRepository>(relaxed = true)
    private val errorMapper = mockk<ErrorMapper>(relaxed = true)
    private val questionMapper = mockk<QuestionMapper>(relaxed = true)

    private val loadSurveyUseCase = LoadSurveyUseCaseImpl(
        repository = repository,
        errorMapper = errorMapper,
        questionMapper = questionMapper,
    )

    @Test
    fun `Given successful repository response, When load, Then return Outcome Success with mapped questions`() =
        runTest {
            // Given
            val questionDataModels = listOf(QuestionDataModel(1, "What is your name?"))
            val questionModels = mapOf(1 to QuestionModel("What is your name?"))

            coEvery { repository.getQuestions() } returns Result.success(questionDataModels)
            coEvery { questionMapper.map(questionDataModels) } returns questionModels

            // When
            val result = loadSurveyUseCase.load()

            // Then
            assertEquals(Outcome.Success(questionModels), result)
        }

    @Test
    fun `Given empty repository response, When load, Then return Outcome Error with NoResultsErrorModel`() = runTest {
        // Given
        val questionDataModels = emptyList<QuestionDataModel>()

        coEvery { repository.getQuestions() } returns Result.success(questionDataModels)

        // When
        val result = loadSurveyUseCase.load()

        // Then
        assertEquals(Outcome.Error(ErrorModel.NoResultsErrorModel), result)
    }

    @Test
    fun `Given repository failure, When load, Then return Outcome Error with mapped error`() = runTest {
        // Given
        val exception = RuntimeException("Repository error")

        coEvery { repository.getQuestions() } returns Result.failure(exception)
        coEvery { errorMapper.mapError(exception) } returns ErrorModel.UnknownErrorModel

        // When
        val result = loadSurveyUseCase.load()

        // Then
        assertEquals(Outcome.Error(ErrorModel.UnknownErrorModel), result)
    }
}