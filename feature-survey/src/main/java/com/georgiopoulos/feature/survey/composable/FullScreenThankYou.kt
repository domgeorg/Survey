package com.georgiopoulos.feature.survey.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.design.widget.text.DesignSystemText
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_1
import com.georgiopoulos.core_resources.R as Resources

@Composable
fun FullScreenThankYou() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("thankYou"),
        contentAlignment = Alignment.Center,
    ) {
        val composition by rememberLottieComposition(
            spec = RawRes(Resources.raw.animation_confetti),
        )

        DesignSystemText(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = DesignSystemTheme.spacings.spacing64),
            text = stringResource(id = Resources.string.thank_you),
            textType = TITLE_1,
            alignment = TextAlign.Center,
        )

        LottieAnimation(
            modifier = Modifier.fillMaxSize(),
            composition = composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = true,
        )
    }
}