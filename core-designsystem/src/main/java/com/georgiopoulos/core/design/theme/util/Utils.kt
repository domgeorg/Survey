package com.georgiopoulos.core.design.theme.util

import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.LineHeightStyle.Alignment
import androidx.compose.ui.text.style.LineHeightStyle.Trim.Companion.None

internal fun TextStyle.withDesignSystemFontCorrection(): TextStyle {
    return merge(
        TextStyle(
            platformStyle = PlatformTextStyle(
                includeFontPadding = false,
            ),
            lineHeightStyle = LineHeightStyle(
                alignment = Alignment.Proportional,
                trim = None,
            ),
        ),
    )
}
