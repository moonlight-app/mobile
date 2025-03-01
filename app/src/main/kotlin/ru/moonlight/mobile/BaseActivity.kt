package ru.moonlight.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.common.di.CoroutineScopeAnnotation
import ru.moonlight.common.di.MoonlightScope
import ru.moonlight.data.repository.AuthRepository
import ru.moonlight.data.repository.CatalogRepository
import ru.moonlight.mobile.ui.MoonlightScreen
import ru.moonlight.mobile.ui.rememberMoonlightAppState
import ru.moonlight.network.utils.NetworkMonitor
import javax.inject.Inject

@AndroidEntryPoint
class BaseActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    @CoroutineScopeAnnotation(scope = MoonlightScope.ApplicationScope)
    lateinit var applicationCoroutineScope: CoroutineScope

    @Inject
    lateinit var catalogRepository: CatalogRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        enableEdgeToEdge()
        setContent {
            val darkTheme = isSystemInDarkTheme()

            // Set icon color for status bar and navigation bar
            // Will remove when enableEdgeToEdge() support it
            val systemUiController = rememberSystemUiController()
            systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = darkTheme)
            systemUiController.setNavigationBarColor(color = Color.Transparent, darkIcons = darkTheme)

            val appState = rememberMoonlightAppState(
                networkMonitor = networkMonitor,
                isUserAuthorize = authRepository.isUserAuthorized,
            )

            MoonlightTheme {
                MoonlightScreen(appState = appState)
            }
        }
    }

    override fun onDestroy() {
        applicationCoroutineScope.launch {
            catalogRepository.clearDatabase()
        }
        super.onDestroy()
    }
}