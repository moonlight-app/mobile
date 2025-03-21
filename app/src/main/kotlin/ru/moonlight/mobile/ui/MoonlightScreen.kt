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
import ru.moonlight.api.component.BadgedBoxComponent
import ru.moonlight.api.component.BottomNavigationComponent
import ru.moonlight.api.component.NavigationBarItemComponent
import ru.moonlight.api.component.SnackbarComponent
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.utils.keyboard.keyboardAsState
import ru.moonlight.feature_catalog.api.navigation.CatalogRoute
import ru.moonlight.mobile.R
import ru.moonlight.mobile.navigation.MoonlightNavHost
import ru.moonlight.mobile.navigation.TopLevelDestination

@Composable
fun MoonlightScreen(
    modifier: Modifier = Modifier,
    appState: MoonlightAppState,
) {
    val context = LocalContext.current
    val currentDestination = appState.currentDestination
    val snackbarHostState = remember { SnackbarHostState() }
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()
    val keyboardVisible by keyboardAsState()

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
                if (!keyboardVisible) {
                    AnimatedVisibility(
                        visible = appState.isCurrentTopLevelDestination || appState.isCurrentPreTopLevelDestination,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically(),
                    ) {
                        BottomNavigationComponent {
                            appState.topLevelDestinations.forEach { destination ->
                                val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
                                NavigationBarItemComponent(
                                    selected = selected,
                                    onClick = {
                                        if (destination == TopLevelDestination.CatalogCategories && currentDestination!!.hasRoute<CatalogRoute>()) {
                                            appState.navController.popBackStack()
                                        } else
                                            appState.navigateToTopLevelDestination(destination)
                                    },
                                    icon = {
                                        BadgedBoxComponent(
                                            badgeCount = destination.badgeCount,
                                            selected = selected,
                                            icon = destination.icon.invoke(),
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            },
            snackbarHost = {
                SnackbarComponent(
                    modifier = Modifier
                        .padding(bottom = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical * 2),
                    snackbarHostState = snackbarHostState
                )
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

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.route.simpleName ?: throw IllegalArgumentException("simpleName is null"), true) ?: false
    } ?: false