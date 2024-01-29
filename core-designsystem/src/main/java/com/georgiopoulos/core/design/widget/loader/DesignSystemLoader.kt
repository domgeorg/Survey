package com.georgiopoulos.core.design.widget.loader

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.design.theme.base.BaseColors
import com.georgiopoulos.core_resources.R as Resources

@Composable
fun DesignSystemLoader(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .testTag("loader")
            .background(BaseColors.neutral100.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center,
    ) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(Resources.raw.animation_loader_circular),
        )
        if (!LocalInspectionMode.current) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .size(DesignSystemTheme.dimensions.loaderDimensions.circularSize),
            )
        } else {
            Box(
                modifier = Modifier
                    .size(DesignSystemTheme.dimensions.loaderDimensions.circularSize)
                    .border(
                        width = 2.dp,
                        color = DesignSystemTheme.colors.primaryColors.purple,
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    "Loader",
                    fontSize = 12.sp,
                    color = DesignSystemTheme.typography.text.title1.color,
                )
            }
        }
    }
}
