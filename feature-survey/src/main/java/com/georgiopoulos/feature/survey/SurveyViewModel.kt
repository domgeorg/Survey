package com.georgiopoulos.feature.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.georgiopoulos.core.domain.model.Outcome.Error
import com.georgiopoulos.core.domain.model.Outcome.Success
import com.georgiopoulos.core.domain.model.answer.AnswerStatus.Invalid
import com.georgiopoulos.core.domain.model.answer.AnswerStatus.Valid
import com.georgiopoulos.core.domain.model.question.QuestionModel
import com.georgiopoulos.core.domain.usecase.load.LoadSurveyUseCase
import com.georgiopoulos.core.domain.usecase.submit.SubmitAnswerUseCase
import com.georgiopoulos.core.domain.usecase.validate.AnswerValidationUseCase
import com.georgiopoulos.core.utils.replaceAndReturn
import com.georgiopoulos.feature.survey.SurveyScreenEvent.LoadSurvey
import com.georgiopoulos.feature.survey.SurveyScreenEvent.RestoreInvalidState
import com.georgiopoulos.feature.survey.SurveyScreenEvent.SubmitAnswer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val loadSurveyUseCase: LoadSurveyUseCase,
    private val answerValidationUseCase: AnswerValidationUseCase,
    private val submitAnswerUseCase: SubmitAnswerUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SurveyUiData())
    val uiState = _uiState.asStateFlow()

    init {
        loadSurvey()
    }

    fun triggerEvent(event: SurveyScreenEvent) {
        when (event) {
            is SubmitAnswer -> validateAndSubmit(event.questionId, event.questionModel)
            LoadSurvey -> loadSurvey()
            RestoreInvalidState -> restoreInvalidState()
        }
    }

    private fun loadSurvey() {
        _uiState.update { previousState ->
            previousState.copy(
                isLoading = true,
                error = null,
            )
        }
        viewModelScope.launch {
            when (val outcome = loadSurveyUseCase.load()) {
                is Error -> _uiState.update { previousState ->
                    previousState.copy(
                        isLoading = false,
                        error = outcome.value,
                    )
                }

                is Success -> _uiState.update { previousState ->
                    previousState.copy(
                        isLoading = false,
                        questions = outcome.value.toMutableMap(),
                    )
                }
            }
        }
    }

    private fun restoreInvalidState() {
        _uiState.update { previousState ->
            previousState.copy(invalidAnswer = null)
        }
    }

    private fun validateAndSubmit(questionId: Int, questionModel: QuestionModel) {
        when (val validationStatus = answerValidationUseCase.validateAnswer(questionModel.answer)) {
            is Invalid -> _uiState.update { previousState ->
                previousState.copy(invalidAnswer = validationStatus.copy())
            }

            Valid -> submitAnswer(questionId, questionModel)
        }
    }

    private fun submitAnswer(questionId: Int, questionModel: QuestionModel) {
        _uiState.update { previousState ->
            previousState.copy(
                isLoading = true,
                submissionResult = null,
            )
        }
        viewModelScope.launch {
            when (submitAnswerUseCase.submit(questionId, questionModel.answer.trim())) {
                is Error -> _uiState.update { previousState ->
                    previousState.copy(
                        isLoading = false,
                        submissionResult = SubmissionResult.Failure(
                            questionId = questionId,
                            questionModel = questionModel,
                        ),
                        questions = previousState.questions.replaceAndReturn(
                            key = questionId,
                            value = questionModel.copy(
                                pendingAnswer = questionModel.answer,
                                answer = "",
                            ),
                        ),
                    )
                }

                is Success -> _uiState.update { previousState ->
                    val submittedAnswers = previousState.submittedAnswersNumber + 1
                    previousState.copy(
                        isLoading = false,
                        submissionResult = SubmissionResult.Success,
                        submittedAnswersNumber = submittedAnswers,
                        isEndOfSurvey = submittedAnswers == previousState.questions.size,
                        questions = previousState.questions.replaceAndReturn(
                            key = questionId,
                            value = questionModel.copy(
                                answer = questionModel.answer,
                                pendingAnswer = "",
                            ),
                        ),
                    )
                }
            }
        }
    }
}
