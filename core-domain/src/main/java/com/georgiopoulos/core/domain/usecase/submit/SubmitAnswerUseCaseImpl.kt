package com.georgiopoulos.core.domain.usecase.submit

import com.georgiopoulos.core.data.repository.SurveyRepository
import com.georgiopoulos.core.domain.model.Outcome
import com.georgiopoulos.core.domain.model.error.mapper.ErrorMapper
import javax.inject.Inject

internal class SubmitAnswerUseCaseImpl @Inject constructor(
    private val repository: SurveyRepository,
    private val errorMapper: ErrorMapper,
) : SubmitAnswerUseCase {

    override suspend fun submit(questionId: Int, answer: String): Outcome<Unit> =
        repository.submitAnswer(questionId, answer).fold(
            onSuccess = { Outcome.Success(Unit) },
            onFailure = { exception ->
                Outcome.Error(errorMapper.mapError(exception))
            }
        )
}