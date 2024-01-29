package com.georgiopoulos.feature.survey.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.georgiopoulos.core.design.Keyboard.Closed
import com.georgiopoulos.core.design.keyboardAsState
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.design.widget.button.DesignSystemButton
import com.georgiopoulos.core.design.widget.input.DesignSystemInputField
import com.georgiopoulos.core.design.widget.text.DesignSystemText
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_1
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_2
import com.georgiopoulos.core.domain.model.question.QuestionModel
import com.georgiopoulos.core_resources.R as Resources

@Composable
fun Question(
    questionModel: QuestionModel,
    onSubmit: (QuestionModel) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var answer by remember { mutableStateOf("") }
    val keyboardState by keyboardAsState()
    if (keyboardState == Closed) {
        focusManager.clearFocus()
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DesignSystemTheme.colors.neutralColors.neutral1)
            .testTag("question"),
    ) {

        DesignSystemText(
            modifier = Modifier
                .padding(DesignSystemTheme.spacings.spacing16),
            textType = TITLE_1,
            text = questionModel.question,
        )

        if (questionModel.answer.isNotEmpty()) {
            DesignSystemText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = DesignSystemTheme.spacings.spacing16,
                        vertical = DesignSystemTheme.spacings.spacing40,
                    ),
                text = questionModel.answer,
                textType = TITLE_2,
            )
        } else {
            DesignSystemInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(DesignSystemTheme.spacings.spacing6),
                text = answer,
                hint = stringResource(id = Resources.string.answer_hint),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text,
                ),
                onActionKeyListener = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    },
                ),
                onValueChange = { text ->
                    answer = text
                },
            )
        }

        DesignSystemButton(
            modifier = Modifier
                .padding(horizontal = DesignSystemTheme.spacings.spacing16),
            enabled = questionModel.answer.isEmpty(),
            text = if (questionModel.answer.isNotEmpty()) {
                stringResource(id = Resources.string.submitted_button)
            } else {
                stringResource(id = Resources.string.submit_button)
            },
        ) {
            onSubmit.invoke(questionModel.copy(answer = answer))
            focusManager.clearFocus()
        }
    }
}
