package com.georgiopoulos.feature.survey.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.design.widget.icon.DesignSystemIcon
import com.georgiopoulos.core.design.widget.icon.IconType.ARROW_BACK
import com.georgiopoulos.core.design.widget.text.DesignSystemText
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_3_INFORMATIVE
import com.georgiopoulos.core_resources.R as Resources

@Composable
fun SurveyNavigationBar(
    modifier: Modifier = Modifier,
    questionnaireSize: Int,
    questionNumber: Int,
    onNavigateToQuestion: (Int) -> Unit,
    onBack: () -> Unit,
) {
    Row(
        modifier = modifier
            .background(DesignSystemTheme.colors.primaryColors.purple)
            .padding(DesignSystemTheme.spacings.spacing16)
            .testTag("navigationBar"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        DesignSystemIcon(
            modifier = Modifier
                .padding(end = DesignSystemTheme.spacings.spacing8)
                .clickable { onBack() },
            enabledColor = DesignSystemTheme.colors.neutralColors.neutral0,
            iconType = ARROW_BACK,
        )

        DesignSystemText(
            modifier = Modifier
                .weight(1f, fill = true)
                .padding(start = DesignSystemTheme.spacings.spacing4),
            text = stringResource(id = Resources.string.question_number, (questionNumber + 1), questionnaireSize),
            textType = TITLE_3_INFORMATIVE,
        )

        DesignSystemText(
            isEnabled = questionNumber != 0,
            text = stringResource(id = Resources.string.previous),
            textType = TITLE_3_INFORMATIVE,
        ) {
            onNavigateToQuestion(questionNumber - 1)
        }

        DesignSystemText(
            modifier = Modifier.padding(
                start = DesignSystemTheme.spacings.spacing16,
            ),
            isEnabled = questionNumber != questionnaireSize - 1,
            text = stringResource(id = Resources.string.next),
            textType = TITLE_3_INFORMATIVE,
        ) {
            onNavigateToQuestion.invoke(questionNumber + 1)
        }
    }
}
