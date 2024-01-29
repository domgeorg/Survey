package com.georgiopoulos.core.design.theme.base

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.georgiopoulos.core_resources.R as Resources

object BaseFonts {
    private val regular: Font = Font(
        resId = Resources.font.roboto_regular,
    )

    private val bold: Font = Font(
        resId = Resources.font.roboto_bold,
        weight = FontWeight.Bold,
    )

    val fontFamily: FontFamily = FontFamily(
        regular,
        bold,
    )
}
