package com.georgiopoulos.core.domain.usecase.load

import com.georgiopoulos.core.data.repository.SurveyRepository
import com.georgiopoulos.core.domain.model.Outcome
import com.georgiopoulos.core.domain.model.error.ErrorModel.NoResultsErrorModel
import com.georgiopoulos.core.domain.model.error.mapper.ErrorMapper
import com.georgiopoulos.core.domain.model.question.QuestionModel
import com.georgiopoulos.core.domain.model.question.mapper.QuestionMapper
import javax.inject.Inject

internal class LoadSurveyUseCaseImpl @Inject constructor(
    private val repository: SurveyRepository,
    private val errorMapper: ErrorMapper,
    private val questionMapper: QuestionMapper,
) : LoadSurveyUseCase {
    override suspend fun load(): Outcome<Map<Int, QuestionModel>> =
        repository.getQuestions().fold(
            onSuccess = { questions ->
                if (questions.isEmpty()) {
                    Outcome.Error(NoResultsErrorModel)
                } else {
                    Outcome.Success(
                        questionMapper.map(questions)
                    )
                }
            },
            onFailure = { exception ->
                Outcome.Error(errorMapper.mapError(exception))
            }
        )
}