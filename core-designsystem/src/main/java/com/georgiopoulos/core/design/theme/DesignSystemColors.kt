package com.georgiopoulos.core.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.georgiopoulos.core.design.theme.base.BaseColors
import com.georgiopoulos.core.design.theme.base.TextColors
import com.georgiopoulos.core.design.theme.base.TextModifierColors
import com.georgiopoulos.core.design.theme.base.TextStateColors

@Immutable
data class DesignSystemColors(
    val primaryColors: PrimaryColors,
    val secondaryColors: SecondaryColors,
    val neutralColors: NeutralColors,
    val buttonColors: ButtonStateColors,
    val text: TextColors,
    val inputFieldBackground: Color,
    val inputFieldBackgroundError: Color,
    val inputFieldCursorColor: Color,
    val inputFieldBorderColor: Color,
    val inputFieldClearButtonColor: Color,
    val inputFieldIconChevronDownColor: Color,
) {
    internal companion object {
        @Composable
        fun build(): DesignSystemColors {
            val primaryColors = PrimaryColors()
            val secondaryColors = SecondaryColors()
            val neutralColors = NeutralColors()
            return DesignSystemColors(
                primaryColors = primaryColors,
                secondaryColors = secondaryColors,
                neutralColors = neutralColors,
                buttonColors = ButtonStateColors(
                    default = ButtonColors(
                        backgroundColor = primaryColors.purple,
                        disabledBackgroundColor = neutralColors.neutral3,
                        textColor = neutralColors.neutral0,
                        disabledTextColor = neutralColors.neutral0,
                        loadingProgressColor = primaryColors.informativeTeal,
                    ),
                    destructive = ButtonColors(
                        backgroundColor = primaryColors.red,
                        disabledBackgroundColor = secondaryColors.lightRed,
                        textColor = neutralColors.neutral0,
                        disabledTextColor = neutralColors.neutral0,
                        loadingProgressColor = neutralColors.neutral0,
                    ),
                ),
                text = buildTextColors(),
                inputFieldBackground = neutralColors.neutral1,
                inputFieldBackgroundError = secondaryColors.backgroundRed,
                inputFieldCursorColor = primaryColors.purple,
                inputFieldBorderColor = primaryColors.purple,
                inputFieldClearButtonColor = primaryColors.purple,
                inputFieldIconChevronDownColor = primaryColors.purple,
            )
        }

        @Composable
        private fun buildTextColors() = TextColors(
            title = TextStateColors(
                normal = TextModifierColors(
                    normal = BaseColors.purple400,
                    disabled = BaseColors.teal100,
                ),
                negative = TextModifierColors(
                    normal = BaseColors.red400,
                    disabled = BaseColors.red200,
                ),
                highlight = TextModifierColors(
                    normal = BaseColors.purple400,
                    disabled = BaseColors.teal200,
                ),
            ),
            body = TextStateColors(
                normal = TextModifierColors(
                    normal = BaseColors.neutral500,
                ),
            ),
            caption = TextStateColors(
                normal = TextModifierColors(
                    normal = BaseColors.neutral500,
                    disabled = BaseColors.teal100,
                ),
                negative = TextModifierColors(
                    normal = BaseColors.red400,
                    disabled = BaseColors.red200,
                ),
            ),
            link = TextStateColors(
                normal = TextModifierColors(
                    normal = BaseColors.purple400,
                    disabled = BaseColors.teal200,
                ),
            ),
        )
    }
}

@Immutable
data class ButtonColors(
    val backgroundColor: Color = Color.Transparent,
    val borderStrokeColor: Color = Color.Transparent,
    val disabledBackgroundColor: Color = Color.Transparent,
    val disabledBorderStrokeColor: Color = Color.Transparent,
    val textColor: Color,
    val disabledTextColor: Color,
    val loadingProgressColor: Color = Color.Transparent,
)

@Immutable
data class ButtonStateColors(
    val default: ButtonColors,
    val destructive: ButtonColors,
) {
    fun getTextColorForState(
        destructive: Boolean,
        enabled: Boolean,
    ): Color =
        if (destructive) {
            this.destructive
        } else {
            default
        }.run {
            if (enabled) {
                textColor
            } else {
                disabledTextColor
            }
        }
}