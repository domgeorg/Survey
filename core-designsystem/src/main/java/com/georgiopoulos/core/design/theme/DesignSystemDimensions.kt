package com.georgiopoulos.core.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class DesignSystemDimensions internal constructor(
    internal val loaderDimensions: LoaderDimensions,
    internal val iconDimensions: IconDimensions,
    internal val iconButtonDimensions: IconButtonDimensions,
) {

    internal companion object {
        @Composable
        fun build(): DesignSystemDimensions =
            DesignSystemDimensions(
                loaderDimensions = LoaderDimensions.build(),
                iconDimensions = IconDimensions.build(),
                iconButtonDimensions = IconButtonDimensions.build(),
            )
    }
}

@Immutable
internal data class LoaderDimensions(
    val circularSize: Dp,
) {
    internal companion object {
        @Composable
        fun build(): LoaderDimensions =
            LoaderDimensions(
                circularSize = 48.dp,
            )
    }
}

@Immutable
internal data class IconDimensions(
    val sizeNormal: Dp,
    val sizeSmall: Dp,
    val paddingNormal: Dp,
    val paddingSmall: Dp,
) {
    companion object {
        @Composable
        fun build(): IconDimensions =
            IconDimensions(
                sizeNormal = 24.dp,
                sizeSmall = 24.dp,
                paddingNormal = 0.dp,
                paddingSmall = 0.dp,
            )
    }
}

@Immutable
internal data class IconButtonDimensions(
    val size: Dp,
    val elevation: Dp,
) {
    companion object {
        @Composable
        fun build(): IconButtonDimensions =
            IconButtonDimensions(
                size = 48.dp,
                elevation = 8.dp,
            )
    }
}
