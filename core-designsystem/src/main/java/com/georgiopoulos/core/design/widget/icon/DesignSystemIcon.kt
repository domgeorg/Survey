package com.georgiopoulos.core.design.widget.icon

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.design.widget.icon.IconSize.NORMAL
import com.georgiopoulos.core.design.widget.icon.IconType.NONE

@Composable
fun DesignSystemIcon(
    modifier: Modifier = Modifier,
    iconType: IconType = NONE,
    iconSize: IconSize = NORMAL,
    enabledColor: Color = DesignSystemTheme.colors.neutralColors.neutral6,
    disabledColor: Color = DesignSystemTheme.colors.neutralColors.neutral3,
) {
    DesignSystemIconImpl(
        modifier = modifier,
        iconId = iconType.resId,
        iconSize = iconSize,
        enabledColor = enabledColor,
        disabledColor = disabledColor,
    )
}
