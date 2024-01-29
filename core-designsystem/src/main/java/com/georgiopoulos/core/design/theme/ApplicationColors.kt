package com.georgiopoulos.core.design.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.georgiopoulos.core.design.theme.base.BaseColors

@Immutable
data class PrimaryColors internal constructor(
    val purple: Color = BaseColors.purple400,
    val red: Color = BaseColors.red400,
    val green: Color = BaseColors.green400,
    val informativeTeal: Color = BaseColors.teal400,
    val highlightTeal: Color = BaseColors.teal300,
    val highlightRed: Color = BaseColors.red300,
)

@Immutable
data class SecondaryColors internal constructor(
    val lightTeal: Color = BaseColors.teal100,
    val lightPurple: Color = BaseColors.purple200,
    val lightRed: Color = BaseColors.red200,
    val backgroundTeal: Color = BaseColors.teal100,
    val backgroundPurple: Color = BaseColors.purple100,
    val backgroundRed: Color = BaseColors.red100,
)

@Immutable
data class NeutralColors internal constructor(
    val neutral0: Color = BaseColors.neutral100,
    val neutral1: Color = BaseColors.neutral200,
    val neutral2: Color = BaseColors.neutral300,
    val neutral3: Color = BaseColors.neutral400,
    val neutral4: Color = BaseColors.neutral500,
    val neutral5: Color = BaseColors.neutral600,
    val neutral6: Color = BaseColors.neutral700,
)
