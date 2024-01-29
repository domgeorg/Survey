package com.georgiopoulos.core.design.widget.icon

import com.georgiopoulos.core_resources.R as Resources

enum class IconType(
    val resId: Int,
) {
    NONE(0),
    EXIT(Resources.drawable.icon_exit),
    INFO(Resources.drawable.icon_info),
    HELP(Resources.drawable.icon_help),
    ARROW_BACK(Resources.drawable.icon_arrow_back),
    ;
}
