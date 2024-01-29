package com.georgiopoulos.core.design.widget.icon

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class IconSize(
    val size: Dp,
    val padding: Dp,
) {
    NORMAL(24.dp, 0.dp),
    SMALL(24.dp, 0.dp),
    BIG(32.dp, 0.dp),
    ;
}
