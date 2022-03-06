package com.example.survey.model.question

import com.example.survey.data.SurveyData

data class SurveyUIModel(
    val surveyData: SurveyData,
    val answer: String? = null,
    val isSubmitted: Boolean = false
)

object Empty
