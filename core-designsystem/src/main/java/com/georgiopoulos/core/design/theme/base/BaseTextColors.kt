package com.georgiopoulos.core.design.theme.base

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class TextColors(
    val title: TextStateColors,
    val body: TextStateColors,
    val caption: TextStateColors,
    val link: TextStateColors,
)

data class TextStateColors(
    val normal: TextModifierColors,
    val negative: TextModifierColors? = null,
    val highlight: TextModifierColors? = null,
)

data class TextModifierColors(
    val normal: Color,
    val disabled: Color? = null,
)
