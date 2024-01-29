package com.georgiopoulos.feature.survey

import com.georgiopoulos.core.domain.model.question.QuestionModel

sealed interface SurveyScreenEvent {
    data class SubmitAnswer(
        val questionId: Int,
        val questionModel: QuestionModel,
    ) : SurveyScreenEvent

    data object LoadSurvey : SurveyScreenEvent

    data object RestoreInvalidState : SurveyScreenEvent
}