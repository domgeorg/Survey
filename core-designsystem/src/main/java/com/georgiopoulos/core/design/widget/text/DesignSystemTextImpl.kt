package com.georgiopoulos.core.design.widget.text

import androidx.compose.foundation.clickable
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.design.theme.Typography
import com.georgiopoulos.core.design.widget.text.MaxLinesConfig.ForcedMaxLines
import com.georgiopoulos.core.design.widget.text.MaxLinesConfig.UseTextStyleValue
import com.georgiopoulos.core.design.widget.text.TextType.BODY_1
import com.georgiopoulos.core.design.widget.text.TextType.BODY_1_HIGHLIGHT
import com.georgiopoulos.core.design.widget.text.TextType.BODY_2
import com.georgiopoulos.core.design.widget.text.TextType.BODY_2_HIGHLIGHT
import com.georgiopoulos.core.design.widget.text.TextType.CAPTION
import com.georgiopoulos.core.design.widget.text.TextType.CAPTION_DESTRUCTIVE
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_1
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_1_DESTRUCTIVE
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_1_INFORMATIVE
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_2
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_2_DESTRUCTIVE
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_2_INFORMATIVE
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_3
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_3_DESTRUCTIVE
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_3_INFORMATIVE
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_4
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_4_DESTRUCTIVE
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_4_INFORMATIVE

@Composable
internal fun DesignSystemTextImpl(
    text: String,
    modifier: Modifier = Modifier,
    alignment: TextAlign = TextAlign.Start,
    textType: TextType = BODY_1,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: MaxLinesConfig = UseTextStyleValue,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    isEnabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    ProvideTextStyle(
        value = DesignSystemTheme.typography.run {
            getTextStyleFromTextType(textType)
        },
    ) {
        Text(
            text = if (textType.isAllCaps) text.uppercase() else text,
            overflow = overflow,
            modifier = modifier
                .alpha(if (isEnabled) 1f else 0.75f)
                .clickable(isEnabled) { onClick.invoke() },
            textAlign = alignment,
            maxLines = when (maxLines) {
                is ForcedMaxLines -> maxLines.maxLines
                UseTextStyleValue -> textType.maxLines
            },
            color = LocalTextStyle.current.color,
            onTextLayout = onTextLayout,
        )
    }
}

@Composable
private fun Typography.getTextStyleFromTextType(textType: TextType): TextStyle =
    when (textType) {
        TITLE_1 -> this.text.title1
        TITLE_1_DESTRUCTIVE -> this.text.title1Destructive
        TITLE_1_INFORMATIVE -> this.text.title1Informative
        TITLE_2 -> this.text.title2
        TITLE_2_DESTRUCTIVE -> this.text.title2Destructive
        TITLE_2_INFORMATIVE -> this.text.title2Informative
        TITLE_3 -> this.text.title3
        TITLE_3_DESTRUCTIVE -> this.text.title3Destructive
        TITLE_3_INFORMATIVE -> this.text.title3Informative
        TITLE_4 -> this.text.title4
        TITLE_4_DESTRUCTIVE -> this.text.title4Destructive
        TITLE_4_INFORMATIVE -> this.text.title4Informative
        BODY_1 -> this.text.body1
        BODY_1_HIGHLIGHT -> this.text.body1Highlight
        BODY_2 -> this.text.body2
        BODY_2_HIGHLIGHT -> this.text.body2Highlight
        CAPTION -> this.text.caption
        CAPTION_DESTRUCTIVE -> this.text.captionDestructive
    }

sealed interface MaxLinesConfig {
    data object UseTextStyleValue : MaxLinesConfig

    data class ForcedMaxLines(
        val maxLines: Int,
    ) : MaxLinesConfig
}
