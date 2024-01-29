package com.georgiopoulos.core.navigation

import androidx.navigation.NamedNavArgument

sealed class AppScreen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList(),
) {

    val name: String = route.appendArguments(navArguments)

    data object Home : AppScreen(
        route = "home",
    )

    data object Survey : AppScreen(
        route = "survey",
    )
}

/**
 * Appends named navigation arguments to the current string route representation.
 *
 * @param navArguments The list of named navigation arguments to be appended.
 * @return The modified string route representation with appended navigation arguments.
 */
private fun String.appendArguments(navArguments: List<NamedNavArgument>): String {
    // Filter out the mandatory arguments that do not have a default value
    val mandatoryArguments = navArguments.filter { it.argument.defaultValue == null }
        .takeIf { it.isNotEmpty() }
        ?.joinToString(separator = "/", prefix = "/") { "{${it.name}}" }
        .orEmpty()
    // Filter out the optional arguments that have a default value
    val optionalArguments = navArguments.filter { it.argument.defaultValue != null }
        .takeIf { it.isNotEmpty() }
        ?.joinToString(separator = "&", prefix = "?") { "${it.name}={${it.name}}" }
        .orEmpty()
    // Concatenate the mandatory arguments and optional arguments to the route string
    return "$this$mandatoryArguments$optionalArguments"
}