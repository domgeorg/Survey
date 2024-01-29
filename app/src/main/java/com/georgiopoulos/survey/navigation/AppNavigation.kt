package com.georgiopoulos.survey.navigation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.georgiopoulos.core.navigation.AppComposeNavigator
import com.georgiopoulos.core.navigation.AppScreen
import com.georgiopoulos.feature.home.HomeScreen
import com.georgiopoulos.feature.survey.SurveyScreen
import com.georgiopoulos.feature.survey.SurveyViewModel

fun NavGraphBuilder.appNavigation(
    composeNavigator: AppComposeNavigator,
) {
    composable(
        route = AppScreen.Home.name,
    ) {
        HomeScreen { screen ->
            composeNavigator.navigate(screen.name)
        }
    }
    composable(
        route = AppScreen.Survey.name,
    ) {
        val surveyViewModel = hiltViewModel<SurveyViewModel>()
        SurveyScreen(
            state = surveyViewModel.uiState.collectAsState(),
            onEvent = { event -> surveyViewModel.triggerEvent(event) },
            onBack = { composeNavigator.navigateUp() }
        )
    }
}
