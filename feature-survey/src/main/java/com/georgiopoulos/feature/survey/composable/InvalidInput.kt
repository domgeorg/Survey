package com.georgiopoulos.feature.survey.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.georgiopoulos.core.design.AnimatedShowHideComposable
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.design.widget.text.DesignSystemText
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_2_INFORMATIVE

@Composable
fun InvalidInput(
    @StringRes message: Int,
    onVisibilityGone: () -> Unit = {},
) {
    AnimatedShowHideComposable(
        onVisibilityChanged = { isVisible ->
            if (!isVisible) {
                onVisibilityGone()
            }
        },
    ) {
        DesignSystemText(
            modifier = Modifier
                .fillMaxWidth()
                .background(DesignSystemTheme.colors.primaryColors.red)
                .padding(DesignSystemTheme.spacings.spacing16)
                .testTag("invalidInput"),
            text = stringResource(id = message),
            textType = TITLE_2_INFORMATIVE,
            alignment = TextAlign.Center,
        )
    }
}