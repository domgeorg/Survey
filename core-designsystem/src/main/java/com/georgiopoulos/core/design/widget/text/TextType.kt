package com.georgiopoulos.core.design.widget.text

import androidx.compose.ui.graphics.Color
import com.georgiopoulos.core.design.theme.base.BaseColors
import com.georgiopoulos.core.design.widget.text.TextSize.LINE_HEIGHT_16
import com.georgiopoulos.core.design.widget.text.TextSize.LINE_HEIGHT_18
import com.georgiopoulos.core.design.widget.text.TextSize.LINE_HEIGHT_20
import com.georgiopoulos.core.design.widget.text.TextSize.LINE_HEIGHT_26
import com.georgiopoulos.core.design.widget.text.TextSize.LINE_HEIGHT_40
import com.georgiopoulos.core.design.widget.text.TextSize.SIZE_TITLE_1
import com.georgiopoulos.core.design.widget.text.TextSize.SIZE_TITLE_2
import com.georgiopoulos.core.design.widget.text.TextSize.SIZE_TITLE_3
import com.georgiopoulos.core.design.widget.text.TextSize.SIZE_TITLE_4
import com.georgiopoulos.core.design.widget.text.TextSize.TEXT_SIZE_12
import com.georgiopoulos.core.design.widget.text.TextSize.TEXT_SIZE_14
import com.georgiopoulos.core.design.widget.text.TextSize.TEXT_SIZE_16

enum class TextType(
    private val id: Int,
    val textColor: Color,
    val textSizeSp: Float,
    val lineHeightSp: Float,
    val isBold: Boolean = true,
    val maxLines: Int = Int.MAX_VALUE,
    val isAllCaps: Boolean = false,
) {
    TITLE_1(0, BaseColors.neutral700, SIZE_TITLE_1, LINE_HEIGHT_40, isBold = true),
    TITLE_2(1, BaseColors.neutral700, SIZE_TITLE_2, LINE_HEIGHT_26, isBold = false),
    TITLE_3(2, BaseColors.neutral700, SIZE_TITLE_3, LINE_HEIGHT_20, isBold = true),
    TITLE_4(3, BaseColors.neutral700, SIZE_TITLE_4, LINE_HEIGHT_18, isBold = true),

    TITLE_1_DESTRUCTIVE(4, BaseColors.red400, SIZE_TITLE_1, LINE_HEIGHT_40),
    TITLE_2_DESTRUCTIVE(5, BaseColors.red400, SIZE_TITLE_2, LINE_HEIGHT_26),
    TITLE_3_DESTRUCTIVE(6, BaseColors.red400, SIZE_TITLE_3, LINE_HEIGHT_20),
    TITLE_4_DESTRUCTIVE(7, BaseColors.red400, SIZE_TITLE_4, LINE_HEIGHT_18),

    TITLE_1_INFORMATIVE(8, BaseColors.neutral100, SIZE_TITLE_1, LINE_HEIGHT_40),
    TITLE_2_INFORMATIVE(9, BaseColors.neutral100, SIZE_TITLE_2, LINE_HEIGHT_26),
    TITLE_3_INFORMATIVE(10, BaseColors.neutral100, SIZE_TITLE_3, LINE_HEIGHT_20),
    TITLE_4_INFORMATIVE(11, BaseColors.neutral100, SIZE_TITLE_4, LINE_HEIGHT_18),

    BODY_1(12, BaseColors.neutral700, TEXT_SIZE_16, LINE_HEIGHT_20, isBold = false),
    BODY_1_HIGHLIGHT(13, BaseColors.neutral200, TEXT_SIZE_14, LINE_HEIGHT_20, isBold = false),

    BODY_2(14, BaseColors.neutral700, TEXT_SIZE_12, LINE_HEIGHT_18, isBold = false),
    BODY_2_HIGHLIGHT(15, BaseColors.neutral200, TEXT_SIZE_12, LINE_HEIGHT_18, isBold = false),

    CAPTION(16, BaseColors.neutral500, TEXT_SIZE_12, LINE_HEIGHT_16, isBold = false),
    CAPTION_DESTRUCTIVE(17, BaseColors.red400, TEXT_SIZE_12, LINE_HEIGHT_16, false),
    ;
}
