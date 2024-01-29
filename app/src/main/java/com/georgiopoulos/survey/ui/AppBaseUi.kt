package com.georgiopoulos.survey.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.georgiopoulos.core.design.AppBackground
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.navigation.AppComposeNavigator
import com.georgiopoulos.survey.navigation.AppNavigationHost

@Composable
fun AppBaseUi(
    composeNavigator: AppComposeNavigator,
) {
    DesignSystemTheme {
        val navHostController = rememberNavController()

        LaunchedEffect(Unit) {
            composeNavigator.handleNavigationCommands(navHostController)
        }

        AppBackground {
            AppNavigationHost(
                navHostController = navHostController,
                composeNavigator = composeNavigator,
            )
        }
    }
}
