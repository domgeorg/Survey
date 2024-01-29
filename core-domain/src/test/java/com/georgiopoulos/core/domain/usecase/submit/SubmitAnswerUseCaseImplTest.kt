package com.georgiopoulos.core.domain.usecase.submit

import com.georgiopoulos.core.data.repository.SurveyRepository
import com.georgiopoulos.core.domain.model.Outcome
import com.georgiopoulos.core.domain.model.error.ErrorModel
import com.georgiopoulos.core.domain.model.error.mapper.ErrorMapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SubmitAnswerUseCaseImplTest {

    private val repository = mockk<SurveyRepository>()
    private val errorMapper = mockk<ErrorMapper>()

    private val submitAnswerUseCase = SubmitAnswerUseCaseImpl(
        repository = repository,
        errorMapper = errorMapper,
    )

    @Test
    fun `Given successful repository response when submit then return Outcome Success`() = runTest {
        // Given
        val questionId = 1
        val answer = "Mock Answer"

        coEvery { repository.submitAnswer(questionId, answer) } returns Result.success(Unit)

        // When
        val result = submitAnswerUseCase.submit(questionId, answer)

        // Then
        assertEquals(Outcome.Success(Unit), result)
    }

    @Test
    fun `Given repository failure when submit then return Outcome Error with mapped error`() = runTest {
        // Given
        val questionId = 1
        val answer = "Mock Answer"
        val exception = RuntimeException("Repository error")

        coEvery { repository.submitAnswer(questionId, answer) } returns Result.failure(exception)
        coEvery { errorMapper.mapError(exception) } returns ErrorModel.UnknownErrorModel

        // When
        val result = submitAnswerUseCase.submit(questionId, answer)

        // Then
        assertEquals(Outcome.Error(ErrorModel.UnknownErrorModel), result)
    }
}