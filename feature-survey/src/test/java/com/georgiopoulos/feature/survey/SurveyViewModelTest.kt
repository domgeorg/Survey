package com.georgiopoulos.feature.survey

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.georgiopoulos.core.domain.model.Outcome
import com.georgiopoulos.core.domain.model.Outcome.Success
import com.georgiopoulos.core.domain.model.answer.AnswerStatus
import com.georgiopoulos.core.domain.model.answer.AnswerStatus.Valid
import com.georgiopoulos.core.domain.model.answer.InvalidReason
import com.georgiopoulos.core.domain.model.error.ErrorModel.UnknownErrorModel
import com.georgiopoulos.core.domain.model.question.QuestionModel
import com.georgiopoulos.core.domain.usecase.load.LoadSurveyUseCase
import com.georgiopoulos.core.domain.usecase.submit.SubmitAnswerUseCase
import com.georgiopoulos.core.domain.usecase.validate.AnswerValidationUseCase
import com.georgiopoulos.feature.survey.SurveyScreenEvent.LoadSurvey
import com.georgiopoulos.feature.survey.SurveyScreenEvent.RestoreInvalidState
import com.georgiopoulos.feature.survey.SurveyScreenEvent.SubmitAnswer
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SurveyViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val loadSurveyUseCase: LoadSurveyUseCase = mockk(relaxed = true)
    private val submitAnswerUseCase: SubmitAnswerUseCase = mockk(relaxed = true)
    private val answerValidationUseCase: AnswerValidationUseCase = mockk(relaxed = true)

    private lateinit var viewModel: SurveyViewModel

    private val dummyQuestions = mutableMapOf(
        1 to QuestionModel("What is your favourite colour?"),
        2 to QuestionModel("What is your favourite food?"),
        3 to QuestionModel("What is your favourite country?"),
        4 to QuestionModel("What is your favourite sport?"),
        5 to QuestionModel("What is your favourite team?")
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // region Load Survey
    @Test
    fun `Given loadSurveyUseCase returns Success, When viewModel is initialized, Then uiState should be updated`() =
        runTest {
            // Given
            coEvery { loadSurveyUseCase.load() } coAnswers { Success(dummyQuestions) }

            // When
            createViewModel()

            // Then
            Assert.assertEquals(
                SurveyUiData(
                    isLoading = false,
                    questions = dummyQuestions,
                ),
                viewModel.uiState.value,
            )
        }

    @Test
    fun `Given loadSurveyUseCase returns Error, When viewModel is initialized, Then uiState should contain error`() =
        runTest {
            // Given
            coEvery { loadSurveyUseCase.load() } coAnswers { Outcome.Error(UnknownErrorModel) }

            // When
            createViewModel()

            // Then
            Assert.assertEquals(
                SurveyUiData(
                    isLoading = false,
                    questions = mutableMapOf(),
                    error = UnknownErrorModel,
                ),
                viewModel.uiState.value,
            )
        }

    @Test
    fun `Given loadSurveyUseCase returns Success, When LoadSurvey is triggered, Then uiState should contain error`() =
        runTest {
            // Given
            coEvery {
                loadSurveyUseCase.load()
            } coAnswers { Outcome.Error(UnknownErrorModel) } coAndThen { Success(dummyQuestions) }

            // When
            createViewModel()
            viewModel.triggerEvent(LoadSurvey)

            // Then
            Assert.assertEquals(
                SurveyUiData(
                    isLoading = false,
                    questions = dummyQuestions,
                ),
                viewModel.uiState.value,
            )
        }
    // endregion

    // region Submit Answer
    @Test
    fun `Given submitAnswerUseCase Success, When SubmitQuestion is triggered, Then uiState should be updated`() =
        runTest {
            // Given
            givenSurveyLoadedSuccessfully()
            val dummyAnswer = SubmitAnswer(
                questionId = 1,
                QuestionModel(
                    question = "What is your favourite colour?",
                    answer = "black",
                )
            )
            every { answerValidationUseCase.validateAnswer("black") } returns Valid
            coEvery {
                submitAnswerUseCase.submit(
                    questionId = dummyAnswer.questionId,
                    answer = dummyAnswer.questionModel.answer
                )
            } coAnswers { Success(Unit) }

            // When
            viewModel.triggerEvent(event = dummyAnswer)

            // Then
            val expectedQuestions = mutableMapOf(
                1 to QuestionModel("What is your favourite colour?", "black"),
                2 to QuestionModel("What is your favourite food?"),
                3 to QuestionModel("What is your favourite country?"),
                4 to QuestionModel("What is your favourite sport?"),
                5 to QuestionModel("What is your favourite team?")
            )
            Assert.assertEquals(
                SurveyUiData(
                    isLoading = false,
                    submissionResult = SubmissionResult.Success,
                    submittedAnswersNumber = 1,
                    isEndOfSurvey = false,
                    questions = expectedQuestions,
                ),
                viewModel.uiState.value,
            )
        }

    @Test
    fun `Given submitAnswerUseCase Error, When SubmitQuestion is triggered, Then uiState should contain error`() =
        runTest {
            // Given
            givenSurveyLoadedSuccessfully()
            val dummyAnswer = SubmitAnswer(
                questionId = 1,
                QuestionModel(
                    question = "What is your favourite colour?",
                    answer = "black",
                )
            )
            every { answerValidationUseCase.validateAnswer("black") } returns Valid
            coEvery {
                submitAnswerUseCase.submit(
                    questionId = dummyAnswer.questionId,
                    answer = dummyAnswer.questionModel.answer,
                )
            } coAnswers { Outcome.Error(UnknownErrorModel) }

            // When
            viewModel.triggerEvent(event = dummyAnswer)

            // Then
            Assert.assertEquals(
                SurveyUiData(
                    isLoading = false,
                    submissionResult = SubmissionResult.Failure(
                        questionId = dummyAnswer.questionId,
                        questionModel = dummyAnswer.questionModel,
                    ),
                    questions = dummyQuestions,
                ),
                viewModel.uiState.value,
            )
        }

    @Test
    fun `Given submitAnswerUseCase Success and last question, When SubmitQuestion is triggered, Then survey should end`() =
        runTest {
            // Given
            val dummyAnswer = SubmitAnswer(
                questionId = 4,
                QuestionModel(
                    question = "What is your favourite sport?",
                    answer = "football",
                )
            )
            coEvery { loadSurveyUseCase.load() } coAnswers {
                Success(
                    mapOf(
                        4 to QuestionModel("What is your favourite sport?"),
                    ),
                )
            }
            every { answerValidationUseCase.validateAnswer("football") } returns Valid
            coEvery {
                submitAnswerUseCase.submit(
                    questionId = dummyAnswer.questionId,
                    answer = dummyAnswer.questionModel.answer,
                )
            } coAnswers { Success(Unit) }

            // When
            createViewModel()
            viewModel.triggerEvent(event = dummyAnswer)

            // Then
            val expectedQuestion = mutableMapOf(
                4 to QuestionModel("What is your favourite sport?", "football"),
            )
            Assert.assertEquals(
                SurveyUiData(
                    isLoading = false,
                    submissionResult = SubmissionResult.Success,
                    submittedAnswersNumber = 1,
                    isEndOfSurvey = true,
                    questions = expectedQuestion,
                ),
                viewModel.uiState.value,
            )
        }

    // endregion

    // region Invalid Answer
    @Test
    fun `Given invalid answer, When SubmitQuestion is triggered, Then uiState should contain the invalid answer error`() =
        runTest {
            // Given
            givenSurveyLoadedSuccessfully()
            val invalidAnswer = AnswerStatus.Invalid(InvalidReason.ContainsSymbols)
            every { answerValidationUseCase.validateAnswer("$#@") } returns invalidAnswer

            // When
            viewModel.triggerEvent(
                event = SubmitAnswer(
                    questionId = 4,
                    QuestionModel(
                        question = "What is your favourite sport?",
                        answer = "$#@",
                    ),
                ),
            )

            // Then
            Assert.assertEquals(
                SurveyUiData(
                    isLoading = false,
                    invalidAnswer = invalidAnswer,
                    questions = dummyQuestions,
                ),
                viewModel.uiState.value,
            )
        }
    // endregion

    // region Restore Invalid State
    @Test
    fun `Given invalid answer, When RestoreInvalidState is triggered, Then uiState should not contain the invalid answer error`() =
        runTest {
            // Given
            givenSurveyLoadedSuccessfully()
            val invalidAnswer = AnswerStatus.Invalid(InvalidReason.ContainsSymbols)
            every { answerValidationUseCase.validateAnswer("$#@") } returns invalidAnswer

            // When
            viewModel.triggerEvent(
                event = SubmitAnswer(
                    questionId = 4,
                    QuestionModel(
                        question = "What is your favourite sport?",
                        answer = "$#@",
                    ),
                ),
            )
            viewModel.triggerEvent(event = RestoreInvalidState)

            // Then
            Assert.assertEquals(
                SurveyUiData(
                    isLoading = false,
                    invalidAnswer = null,
                    questions = dummyQuestions,
                ),
                viewModel.uiState.value,
            )
        }
    // endregion

    private fun createViewModel() {
        viewModel = SurveyViewModel(
            loadSurveyUseCase = loadSurveyUseCase,
            submitAnswerUseCase = submitAnswerUseCase,
            answerValidationUseCase = answerValidationUseCase,
        )
    }

    private fun givenSurveyLoadedSuccessfully() {
        coEvery { loadSurveyUseCase.load() } coAnswers { Success(dummyQuestions) }
        createViewModel()
    }
}