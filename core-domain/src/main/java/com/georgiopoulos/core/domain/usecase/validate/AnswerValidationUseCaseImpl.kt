package com.georgiopoulos.core.domain.usecase.validate

import com.georgiopoulos.core.domain.model.answer.AnswerStatus
import com.georgiopoulos.core.domain.model.answer.InvalidReason
import javax.inject.Inject

internal class AnswerValidationUseCaseImpl @Inject constructor() : AnswerValidationUseCase {

    override fun validateAnswer(answer: String): AnswerStatus {
        return when {
            answer.trim().isBlank() -> AnswerStatus.Invalid(InvalidReason.Empty)
            answer.trim().length > MAX_CHARACTERS_LIMIT -> AnswerStatus.Invalid(InvalidReason.MaxCharacters)
            containsSymbols(answer) -> AnswerStatus.Invalid(InvalidReason.ContainsSymbols)
            else -> AnswerStatus.Valid
        }
    }

    private fun containsSymbols(input: String): Boolean {
        val symbolPattern = "[!\"#$%&'()*+,-./:;\\\\<=>?@\\[\\]^_`{|}~]".toRegex()
        return input.any { it.toString().matches(symbolPattern) }
    }

    companion object {
        private const val MAX_CHARACTERS_LIMIT = 155
    }
}