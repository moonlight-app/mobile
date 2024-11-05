package ru.moonlight.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import ru.moonlight.mobile.ui.MoonlightScreen
import ru.moonlight.mobile.ui.rememberMoonlightAppState
import ru.moonlight.network.utils.NetworkMonitor
import ru.moonlight.theme.MoonlightTheme
import javax.inject.Inject

@AndroidEntryPoint
class BaseActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = rememberMoonlightAppState(networkMonitor = networkMonitor)

            MoonlightTheme {
                MoonlightScreen(appState = appState)
            }
        }
    }
}