package com.georgiopoulos.feature.survey

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.design.widget.loader.DesignSystemLoader
import com.georgiopoulos.core.design.widget.text.DesignSystemText
import com.georgiopoulos.core_resources.R.string
import com.georgiopoulos.feature.survey.SubmissionResult.Failure
import com.georgiopoulos.feature.survey.SubmissionResult.Success
import com.georgiopoulos.feature.survey.SurveyScreenEvent.LoadSurvey
import com.georgiopoulos.feature.survey.SurveyScreenEvent.RestoreInvalidState
import com.georgiopoulos.feature.survey.composable.FullScreenError
import com.georgiopoulos.feature.survey.composable.FullScreenThankYou
import com.georgiopoulos.feature.survey.composable.InvalidInput
import com.georgiopoulos.feature.survey.composable.Question
import com.georgiopoulos.feature.survey.composable.SubmissionFailure
import com.georgiopoulos.feature.survey.composable.SuccessSubmission
import com.georgiopoulos.feature.survey.composable.SurveyNavigationBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SurveyScreen(
    state: State<SurveyUiData>,
    onEvent: (SurveyScreenEvent) -> Unit = {},
    onBack: () -> Unit = {},
) {
    BackHandler { onBack() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DesignSystemTheme.colors.neutralColors.neutral0),
    ) {
        val focusManager = LocalFocusManager.current
        val pagerState = rememberPagerState { state.value.questions.size }
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { _ ->
                focusManager.clearFocus()
            }
        }

        if (state.value.isLoading) {
            DesignSystemLoader(modifier = Modifier.fillMaxSize())
            return
        }

        state.value.error?.also { error ->
            FullScreenError(error) { onEvent(LoadSurvey) }
            return
        }

        if (state.value.isEndOfSurvey) {
            FullScreenThankYou()
            return
        }

        SurveyNavigationBar(
            questionnaireSize = pagerState.pageCount,
            questionNumber = pagerState.currentPage,
            onNavigateToQuestion = { questionNumber ->
                coroutineScope.launch {
                    focusManager.clearFocus()
                    pagerState.animateScrollToPage(questionNumber)
                }
            },
            onBack = { onBack() }
        )

        DesignSystemText(
            modifier = Modifier
                .fillMaxWidth()
                .background(DesignSystemTheme.colors.neutralColors.neutral2)
                .padding(DesignSystemTheme.spacings.spacing16)
                .testTag("submittedQuestions"),
            text = stringResource(id = string.questions_submitted, state.value.submittedAnswersNumber),
            alignment = TextAlign.Center,
        )

        state.value.submissionResult?.also { result ->
            when (result) {
                is Failure -> SubmissionFailure {
                    onEvent(
                        SurveyScreenEvent.SubmitAnswer(
                            questionId = result.questionId,
                            questionModel = result.questionModel,
                        )
                    )
                }

                Success -> SuccessSubmission()
            }
        }

        state.value.invalidAnswer?.also { invalid ->
            InvalidInput(invalid.reason.messageResId) {
                onEvent(RestoreInvalidState)
            }
        }

        HorizontalPager(state = pagerState) { page ->
            Question(
                questionModel = state.value.questions.values.elementAt(page),
                onSubmit = { questionModel ->
                    onEvent(
                        SurveyScreenEvent.SubmitAnswer(
                            questionId = state.value.questions.keys.elementAt(page),
                            questionModel = questionModel,
                        )
                    )
                },
            )
        }
    }
}
