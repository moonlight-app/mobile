package ru.moonlight.mobile.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import ru.moonlight.mobile.R
import ru.moonlight.mobile.navigation.MoonlightNavHost
import ru.moonlight.theme.MoonlightTheme
import ru.moonlight.ui.BadgedBoxComponent
import ru.moonlight.ui.BottomNavigationComponent
import ru.moonlight.ui.NavigationBarItemComponent
import ru.moonlight.ui.SnackbarHostComponent
import kotlin.reflect.KClass

@Composable
fun MoonlightScreen(
    modifier: Modifier = Modifier,
    appState: MoonlightAppState,
) {
    val context = LocalContext.current
    val currentDestination = appState.currentDestination
    val isUserAuthorized = appState.isUserAuthorized
    val snackbarHostState = remember { SnackbarHostState() }
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

    // If user is not connected to the internet show a snack bar to inform them
    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = context.getString(R.string.checkInternetConnection),
                duration = SnackbarDuration.Indefinite,
            )
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize(),
        color = MoonlightTheme.colors.card
    ) {
        Scaffold(
            containerColor = MoonlightTheme.colors.card,
            contentWindowInsets = WindowInsets.safeDrawing.only(WindowInsetsSides.Top),
            bottomBar = {
                AnimatedVisibility(
                    visible = appState.isCurrentTopLevelDestination,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically(),
                ) {
                    BottomNavigationComponent {
                        appState.topLevelDestinations.forEach { destination ->
                            val selected = currentDestination.isRouteInHierarchy(destination.route)
                            NavigationBarItemComponent(
                                selected = selected,
                                onClick = { appState.navigateToTopLevelDestination(destination) },
                                icon = {
                                    BadgedBoxComponent(
                                        badgeCount = destination.badgeCount,
                                        selected = selected,
                                        selectedIcon = destination.selectedIcon,
                                        unselectedIcon = destination.unselectedIcon,
                                    )
                                }
                            )
                        }
                    }
                }
            },
            snackbarHost = {
                SnackbarHostComponent(snackbarHostState = snackbarHostState)
            },

        ) { paddingValues ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(bottom = paddingValues.calculateBottomPadding())
                    .background(color = MoonlightTheme.colors.background),
            ) {
                MoonlightNavHost(appState = appState)
            }
        }
    }
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false