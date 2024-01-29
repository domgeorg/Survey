package com.georgiopoulos.core.design.widget.button

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.design.widget.button.ButtonSize.NORMAL
import com.georgiopoulos.core.design.widget.button.ButtonSize.SMALL
import com.georgiopoulos.core.design.widget.text.TextSize

@Composable
fun DesignSystemButton(
    text: String,
    modifier: Modifier = Modifier,
    buttonSize: ButtonSize = NORMAL,
    destructive: Boolean = false,
    enabled: Boolean = true,
    hapticFeedbackEnabled: Boolean = false,
    onClick: () -> Unit,
) {
    val view = LocalView.current

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Button(
            colors = resolveButtonColors(destructive),
            enabled = enabled,
            elevation = null,
            shape = RoundedCornerShape(
                size = 8.dp,
            ),
            modifier = when (buttonSize) {
                SMALL -> {
                    Modifier
                        .wrapContentWidth()
                        .defaultMinSize(
                            minHeight = SMALL.size,
                        )
                }

                NORMAL -> {
                    Modifier
                        .fillMaxWidth()
                        .defaultMinSize(
                            minHeight = NORMAL.size,
                        )
                }
            },
            onClick = {
                if (hapticFeedbackEnabled) {
                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                }
                onClick()
            },
            contentPadding = PaddingValues(
                vertical = DesignSystemTheme.spacings.spacing4,
                horizontal = DesignSystemTheme.spacings.spacing16,
            ),
        ) {
            Text(
                text = text,
                style = DesignSystemTheme.typography.button,
                fontSize = TextSize.TEXT_SIZE_16.sp,
                maxLines = 1,
                color = DesignSystemTheme.colors.buttonColors.getTextColorForState(
                    destructive = destructive,
                    enabled = enabled,
                ),
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun resolveButtonColors(destructive: Boolean) = if (destructive) {
    ButtonDefaults.buttonColors(
        containerColor = DesignSystemTheme.colors.buttonColors.destructive.backgroundColor,
        disabledContainerColor = DesignSystemTheme.colors.buttonColors.destructive.disabledBackgroundColor,
        contentColor = DesignSystemTheme.colors.buttonColors.destructive.textColor,
        disabledContentColor = DesignSystemTheme.colors.buttonColors.destructive.disabledTextColor,
    )
} else {
    ButtonDefaults.buttonColors(
        containerColor = DesignSystemTheme.colors.buttonColors.default.backgroundColor,
        disabledContainerColor = DesignSystemTheme.colors.buttonColors.default.disabledBackgroundColor,
        contentColor = DesignSystemTheme.colors.buttonColors.default.textColor,
        disabledContentColor = DesignSystemTheme.colors.buttonColors.default.disabledTextColor,
    )
}
