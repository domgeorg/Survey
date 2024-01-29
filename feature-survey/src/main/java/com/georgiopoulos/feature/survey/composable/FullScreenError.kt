package com.georgiopoulos.feature.survey.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.design.widget.button.DesignSystemButton
import com.georgiopoulos.core.design.widget.icon.DesignSystemIcon
import com.georgiopoulos.core.design.widget.icon.IconSize.BIG
import com.georgiopoulos.core.design.widget.icon.IconType.INFO
import com.georgiopoulos.core.design.widget.text.DesignSystemText
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_2_DESTRUCTIVE
import com.georgiopoulos.core.domain.model.error.ErrorModel
import com.georgiopoulos.core_resources.R as Resources

@Composable
fun FullScreenError(
    error: ErrorModel,
    onRetry: () -> Unit,
) {
    Box(
        modifier = Modifier
            .testTag("surveyScreenError")
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            DesignSystemIcon(
                modifier = Modifier.padding(DesignSystemTheme.spacings.spacing8),
                iconSize = BIG,
                enabledColor = DesignSystemTheme.colors.primaryColors.red,
                iconType = INFO,
            )

            DesignSystemText(
                modifier = Modifier.padding(
                    vertical = DesignSystemTheme.spacings.spacing24,
                    horizontal = DesignSystemTheme.spacings.spacing16
                ),
                alignment = TextAlign.Center,
                textType = TITLE_2_DESTRUCTIVE,
                text = stringResource(id = error.errorMessageResId),
            )

            DesignSystemButton(
                modifier = Modifier
                    .padding(horizontal = DesignSystemTheme.spacings.spacing16),
                destructive = true,
                text = stringResource(id = Resources.string.retry),
            ) { onRetry() }
        }
    }
}