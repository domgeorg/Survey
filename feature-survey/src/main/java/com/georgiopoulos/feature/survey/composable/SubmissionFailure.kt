package com.georgiopoulos.feature.survey.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.georgiopoulos.core.design.AnimatedShowHideComposable
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.design.widget.text.DesignSystemText
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_2_INFORMATIVE
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_3_INFORMATIVE
import com.georgiopoulos.core_resources.R.string

const val SHOW_TIME_MILLIS_FAILURE = 3000L

@Composable
fun SubmissionFailure(
    onRetry: () -> Unit,
) {
    AnimatedShowHideComposable(showTimeMillis = SHOW_TIME_MILLIS_FAILURE) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DesignSystemTheme.colors.primaryColors.red)
                .padding(DesignSystemTheme.spacings.spacing16)
                .testTag("submissionFailure"),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            DesignSystemText(
                modifier = Modifier.weight(1f, fill = true),
                text = stringResource(id = string.error_submission),
                textType = TITLE_2_INFORMATIVE
            )

            DesignSystemText(
                text = stringResource(id = string.retry),
                textType = TITLE_3_INFORMATIVE
            ){ onRetry() }
        }
    }
}
