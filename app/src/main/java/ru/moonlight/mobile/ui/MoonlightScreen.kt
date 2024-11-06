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
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import ru.moonlight.mobile.navigation.MoonlightNavHost
import ru.moonlight.theme.MoonlightTheme
import ru.moonlight.ui.BottomNavigationComponent
import ru.moonlight.ui.NavigationBarItemComponent
import kotlin.reflect.KClass

@Composable
fun MoonlightScreen(
    modifier: Modifier = Modifier,
    appState: MoonlightAppState,
) {
    val currentDestination = appState.currentDestination
    val isUserAuthorized = appState.isUserAuthorized

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
                    visible = isUserAuthorized.value || appState.isCurrentTopLevelDestination,
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
                                    BadgedBox(
                                        badge = {
                                            if(destination.badgeCount != null) {
                                                Badge(
                                                    containerColor = MoonlightTheme.colors.highlightComponent,
                                                    contentColor = MoonlightTheme.colors.text,
                                                ) {
                                                    Text(text = destination.badgeCount.toString())
                                                }
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = if (selected) {
                                                destination.selectedIcon
                                            } else destination.unselectedIcon,
                                            contentDescription = null
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
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