package com.georgiopoulos.core.domain.usecase.load

import com.georgiopoulos.core.domain.model.Outcome
import com.georgiopoulos.core.domain.model.question.QuestionModel

interface LoadSurveyUseCase {

    suspend fun load(): Outcome<Map<Int, QuestionModel>>
}