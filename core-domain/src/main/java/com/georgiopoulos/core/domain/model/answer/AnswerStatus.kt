package com.georgiopoulos.core.domain.model.answer

import androidx.annotation.StringRes
import com.georgiopoulos.core_resources.R as Resources

sealed interface AnswerStatus {
    data class Invalid(val reason: InvalidReason) : AnswerStatus

    data object Valid : AnswerStatus
}

sealed class InvalidReason(@StringRes val messageResId: Int) {
    data object Empty : InvalidReason(Resources.string.empty_answer)
    data object MaxCharacters : InvalidReason(Resources.string.max_characters)
    data object ContainsSymbols: InvalidReason(Resources.string.contains_symbols)
}