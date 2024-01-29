package com.georgiopoulos.feature.survey.composable

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
import com.georgiopoulos.core_resources.R.string

@Composable
fun SuccessSubmission() {
    AnimatedShowHideComposable {
        DesignSystemText(
            modifier = Modifier
                .fillMaxWidth()
                .background(DesignSystemTheme.colors.primaryColors.green)
                .padding(DesignSystemTheme.spacings.spacing16)
                .testTag("successSubmission"),
            text = stringResource(id = string.success_submission),
            textType = TITLE_2_INFORMATIVE,
            alignment = TextAlign.Center,
        )
    }
}
