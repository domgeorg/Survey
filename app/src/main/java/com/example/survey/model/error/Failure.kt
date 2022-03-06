package com.example.survey.model.error

sealed interface Failure {
    object SurveyDataError : Failure
    object SubmitError : Failure
    object InvalidInput : Failure
}