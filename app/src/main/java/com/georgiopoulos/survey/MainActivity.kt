package com.georgiopoulos.survey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.navigation.AppComposeNavigator
import com.georgiopoulos.survey.ui.AppBaseUi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    internal lateinit var appComposeNavigator: AppComposeNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DesignSystemTheme {
                AppBaseUi(composeNavigator = appComposeNavigator)
            }
        }
    }
}
