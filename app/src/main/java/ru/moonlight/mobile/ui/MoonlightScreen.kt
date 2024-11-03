package ru.moonlight.mobile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        color = MoonlightTheme.colors.card
    ) {
        Scaffold(
            bottomBar = {
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
                                            Badge {
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
        ) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(color = MoonlightTheme.colors.background),


            ) {
                MoonlightNavHost(appState = appState, modifier = modifier.padding(it))
            }
        }
    }


}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false
