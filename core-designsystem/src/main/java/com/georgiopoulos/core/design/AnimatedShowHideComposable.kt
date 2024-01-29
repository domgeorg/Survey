package com.georgiopoulos.core.design

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

const val SHOW_TIME_MILLIS = 2000L

@Composable
fun AnimatedShowHideComposable(
    showTimeMillis: Long = SHOW_TIME_MILLIS,
    onVisibilityChanged: (Boolean) -> Unit = {},
    content: @Composable () -> Unit,
) {
    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(visible) {
        delay(showTimeMillis)
        visible = false
        onVisibilityChanged(false)
    }

    AnimatedVisibility(
        visible = visible,
    ) {
        onVisibilityChanged(visible)
        content()
    }
}