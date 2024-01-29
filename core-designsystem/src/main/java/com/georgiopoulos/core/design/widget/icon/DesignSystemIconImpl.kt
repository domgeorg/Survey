package com.georgiopoulos.core.design.widget.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.design.widget.icon.IconSize.NORMAL

@Composable
internal fun DesignSystemIconImpl(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @DrawableRes iconId: Int,
    iconSize: IconSize = NORMAL,
    contentDescription: String? = null,
    enabledColor: Color = DesignSystemTheme.colors.neutralColors.neutral5,
    disabledColor: Color = DesignSystemTheme.colors.neutralColors.neutral3,
) {
    val widgetSize = iconSize.size
    val paddingSize = iconSize.padding
    Box(
        modifier = modifier
            .size(widgetSize),
        contentAlignment = Alignment.Center,
    ) {
        if (iconId != 0) {
            Image(
                painter = painterResource(id = iconId),
                contentDescription = contentDescription,
                modifier = Modifier
                    .size(widgetSize - (paddingSize * 2)),
                colorFilter = ColorFilter.tint(
                    if (enabled) {
                        enabledColor
                    } else {
                        disabledColor
                    },
                ),
            )
        }
    }
}
