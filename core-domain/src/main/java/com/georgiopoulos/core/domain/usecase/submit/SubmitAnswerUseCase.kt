package com.georgiopoulos.core.domain.usecase.submit

import com.georgiopoulos.core.domain.model.Outcome

interface SubmitAnswerUseCase {

    suspend fun submit(questionId: Int, answer: String): Outcome<Unit>
}