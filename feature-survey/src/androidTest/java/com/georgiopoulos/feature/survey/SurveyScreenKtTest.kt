package com.georgiopoulos.feature.survey

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.domain.model.answer.AnswerStatus
import com.georgiopoulos.core.domain.model.answer.InvalidReason
import com.georgiopoulos.core.domain.model.error.ErrorModel
import com.georgiopoulos.core.domain.model.question.QuestionModel
import com.georgiopoulos.feature.survey.SubmissionResult.Success
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SurveyScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyQuestions = mutableMapOf(
        1 to QuestionModel("What is your favourite colour?"),
        2 to QuestionModel("What is your favourite food?"),
        3 to QuestionModel("What is your favourite country?"),
        4 to QuestionModel("What is your favourite sport?"),
        5 to QuestionModel("What is your favourite team?")
    )

    @Test
    fun when_survey_is_loading_then_display_loader() {
        composeTestRule.setContent {
            DesignSystemTheme {
                SurveyScreen(
                    state = remember {
                        mutableStateOf(SurveyUiData(isLoading = true))
                    }
                )
            }
        }

        composeTestRule.onNodeWithTag("loader").assertIsDisplayed()
    }

    @Test
    fun when_survey_has_error_then_display_error_message() {
        composeTestRule.setContent {
            DesignSystemTheme {
                SurveyScreen(
                    state = remember {
                        mutableStateOf(
                            SurveyUiData(
                                isLoading = false,
                                error = ErrorModel.UnknownErrorModel,
                            )
                        )
                    }
                )
            }
        }

        composeTestRule.onNodeWithTag("loader").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("surveyScreenError").assertIsDisplayed()
    }

    @Test
    fun when_survey_is_completed_then_display_thank_you_message() {
        composeTestRule.setContent {
            DesignSystemTheme {
                SurveyScreen(
                    state = remember {
                        mutableStateOf(
                            SurveyUiData(
                                isLoading = false,
                                error = null,
                                isEndOfSurvey = true
                            )
                        )
                    }
                )
            }
        }

        composeTestRule.onNodeWithTag("loader").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("surveyScreenError").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("thankYou").assertIsDisplayed()
    }

    @Test
    fun when_survey_is_in_progress_then_display_navigation_bar_submitted_questions_and_current_question() {
        composeTestRule.setContent {
            DesignSystemTheme {
                SurveyScreen(
                    state = remember {
                        mutableStateOf(
                            SurveyUiData(
                                isLoading = false,
                                error = null,
                                isEndOfSurvey = false,
                                questions = dummyQuestions,
                            )
                        )
                    }
                )
            }
        }

        composeTestRule.onNodeWithTag("loader").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("surveyScreenError").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("thankYou").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("navigationBar").assertIsDisplayed()
        composeTestRule.onNodeWithTag("submittedQuestions").assertIsDisplayed()
        composeTestRule.onNodeWithTag("question").assertIsDisplayed()
    }

    @Test
    fun when_survey_has_invalid_input_then_display_invalid_input_message() {
        composeTestRule.setContent {
            DesignSystemTheme {
                SurveyScreen(
                    state = remember {
                        mutableStateOf(
                            SurveyUiData(
                                isLoading = false,
                                error = null,
                                isEndOfSurvey = false,
                                questions = dummyQuestions,
                                invalidAnswer = AnswerStatus.Invalid(InvalidReason.Empty)
                            )
                        )
                    }
                )
            }
        }

        composeTestRule.onNodeWithTag("loader").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("surveyScreenError").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("thankYou").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("navigationBar").assertIsDisplayed()
        composeTestRule.onNodeWithTag("submittedQuestions").assertIsDisplayed()
        composeTestRule.onNodeWithTag("question").assertIsDisplayed()
        composeTestRule.onNodeWithTag("invalidInput").assertIsDisplayed()
    }

    @Test
    fun when_survey_submission_is_successful_then_display_success_message() {
        composeTestRule.setContent {
            DesignSystemTheme {
                SurveyScreen(
                    state = remember {
                        mutableStateOf(
                            SurveyUiData(
                                isLoading = false,
                                error = null,
                                isEndOfSurvey = false,
                                questions = dummyQuestions,
                                submissionResult = Success,
                            )
                        )
                    }
                )
            }
        }

        composeTestRule.onNodeWithTag("loader").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("surveyScreenError").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("thankYou").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("navigationBar").assertIsDisplayed()
        composeTestRule.onNodeWithTag("submittedQuestions").assertIsDisplayed()
        composeTestRule.onNodeWithTag("question").assertIsDisplayed()
        composeTestRule.onNodeWithTag("successSubmission").assertIsDisplayed()
        composeTestRule.onNodeWithTag("submissionFailure").assertIsNotDisplayed()
    }

    @Test
    fun when_survey_submission_fails_then_display_submission_failure_message() {
        composeTestRule.setContent {
            DesignSystemTheme {
                SurveyScreen(
                    state = remember {
                        mutableStateOf(
                            SurveyUiData(
                                isLoading = false,
                                error = null,
                                isEndOfSurvey = false,
                                questions = dummyQuestions,
                                submissionResult = SubmissionResult.Failure(
                                    questionId = 1,
                                    questionModel = QuestionModel("What is your favourite colour?")
                                ),
                            )
                        )
                    }
                )
            }
        }

        composeTestRule.onNodeWithTag("loader").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("surveyScreenError").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("thankYou").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("navigationBar").assertIsDisplayed()
        composeTestRule.onNodeWithTag("submittedQuestions").assertIsDisplayed()
        composeTestRule.onNodeWithTag("question").assertIsDisplayed()
        composeTestRule.onNodeWithTag("submissionFailure").assertIsDisplayed()
        composeTestRule.onNodeWithTag("successSubmission").assertIsNotDisplayed()
    }

}
