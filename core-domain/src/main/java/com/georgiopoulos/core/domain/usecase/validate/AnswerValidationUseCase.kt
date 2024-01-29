package com.georgiopoulos.core.domain.usecase.validate

import com.georgiopoulos.core.domain.model.answer.AnswerStatus

interface AnswerValidationUseCase {

    fun validateAnswer(answer: String): AnswerStatus
}