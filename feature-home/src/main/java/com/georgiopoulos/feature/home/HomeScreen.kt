package com.georgiopoulos.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.design.widget.button.DesignSystemButton
import com.georgiopoulos.core.design.widget.text.DesignSystemText
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_1
import com.georgiopoulos.core.navigation.AppScreen
import com.georgiopoulos.core_resources.R as Resources

@Composable
fun HomeScreen(
    onNavigationEvent: (AppScreen) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DesignSystemTheme.colors.neutralColors.neutral1),
        contentAlignment = Alignment.Center,
    ) {

        DesignSystemText(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = DesignSystemTheme.spacings.spacing64),
            text = stringResource(id = Resources.string.welcome),
            textType = TITLE_1,
            alignment = TextAlign.Center,
        )

        DesignSystemButton(
            modifier = Modifier
                .padding(horizontal = DesignSystemTheme.spacings.spacing16),
            text = stringResource(id = Resources.string.start_button),
        ) { onNavigationEvent(AppScreen.Survey) }
    }
}