package com.georgiopoulos.core.navigation

import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import javax.inject.Inject

class ComposeNavigator @Inject constructor() : AppComposeNavigator() {

    override fun navigate(
        route: String,
        optionsBuilder: (NavOptionsBuilder.() -> Unit)?,
    ) {
        val options = optionsBuilder?.let { navOptions(it) }
        navigationCommands.tryEmit(
            value = ComposeNavigationCommand.NavigateToRoute(
                route = route,
                options = options,
            ),
        )
    }

    override fun navigateAndClearBackStack(route: String) {
        navigationCommands.tryEmit(
            value = ComposeNavigationCommand.NavigateToRoute(
                route = route,
                options = navOptions {
                    popUpTo(0)
                }
            )
        )
    }

    override fun popUpTo(
        route: String,
        inclusive: Boolean,
    ) {
        navigationCommands.tryEmit(
            value = ComposeNavigationCommand.PopUpToRoute(
                route = route,
                inclusive = inclusive,
            )
        )
    }

    override fun <T> navigateBackWithResult(
        key: String,
        result: T,
        route: String?,
    ) {
        navigationCommands.tryEmit(
            ComposeNavigationCommand.NavigateUpWithResult(
                key = key,
                result = result,
                route = route
            )
        )
    }
}
