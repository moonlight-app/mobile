package ru.moonlight.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import ru.moonlight.mobile.ui.MoonlightScreen
import ru.moonlight.mobile.ui.rememberMoonlightAppState
import ru.moonlight.theme.MoonlightTheme

@AndroidEntryPoint
class BaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val appState = rememberMoonlightAppState()

            MoonlightTheme {
                MoonlightScreen(appState = appState)
            }
        }
    }
}