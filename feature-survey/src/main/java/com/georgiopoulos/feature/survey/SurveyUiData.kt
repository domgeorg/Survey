package com.georgiopoulos.feature.survey

import androidx.compose.runtime.Immutable
import com.georgiopoulos.core.domain.model.answer.AnswerStatus
import com.georgiopoulos.core.domain.model.error.ErrorModel
import com.georgiopoulos.core.domain.model.question.QuestionModel

@Immutable
data class SurveyUiData(
    val isLoading: Boolean = true,
    val questions: MutableMap<Int, QuestionModel> = linkedMapOf(),
    val error: ErrorModel? = null,
    val invalidAnswer: AnswerStatus.Invalid? = null,
    val submittedAnswersNumber: Int = 0,
    val submissionResult: SubmissionResult? = null,
    val isEndOfSurvey: Boolean = false,
)

sealed interface SubmissionResult {
    data object Success : SubmissionResult
    data class Failure(
        val questionId: Int,
        val questionModel: QuestionModel,
    ) : SubmissionResult
}
