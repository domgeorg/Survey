package com.georgiopoulos.core.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

/**
 * Local providers for connecting styling properties to our components.
 */
private val LocalColors = compositionLocalOf<DesignSystemColors> {
    error("No colors provided! Make sure to wrap all DesignSystem components in an DesignSystemTheme.")
}

private val LocalTypography = compositionLocalOf<Typography> {
    error("No typography provided! Make sure to wrap all DesignSystem components in an DesignSystemTheme.")
}

private val LocalDimensions = compositionLocalOf<DesignSystemDimensions> {
    error("No dimensions provided! Make sure to wrap all DesignSystem components in an DesignSystemTheme.")
}

private val LocalSpacings = compositionLocalOf<DesignSystemSpacings> {
    error("No spacings provided! Make sure to wrap all DesignSystem components in an DesignSystemTheme.")
}

@Composable
fun DesignSystemTheme(
    colors: DesignSystemColors = DesignSystemColors.build(),
    typography: Typography = Typography.build(),
    dimensions: DesignSystemDimensions = DesignSystemDimensions.build(),
    spacings: DesignSystemSpacings = DesignSystemSpacings(),
    content: @Composable () -> Unit,
) {

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography,
        LocalDimensions provides dimensions,
        LocalSpacings provides spacings,
    ) {
        content()
    }
}

object DesignSystemTheme {
    /**
     * These represent the default accessors for different properties used to style DesignSystem
     * and custom components.
     */
    val colors: DesignSystemColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val dimensions: DesignSystemDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensions.current

    val spacings: DesignSystemSpacings
        @Composable
        @ReadOnlyComposable
        get() = LocalSpacings.current
}
