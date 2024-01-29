package com.georgiopoulos.survey.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.georgiopoulos.core.navigation.AppComposeNavigator
import com.georgiopoulos.core.navigation.AppScreen

@Composable
fun AppNavigationHost(
    navHostController: NavHostController,
    composeNavigator: AppComposeNavigator,
) {
    NavHost(
        navController = navHostController,
        startDestination = AppScreen.Home.name,
    ) {
        appNavigation(
            composeNavigator = composeNavigator,
        )
    }
}
