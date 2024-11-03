package ru.moonlight.mobile.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.moonlight.feature_cart.navigation.navigateToCartScreen
import ru.moonlight.feature_catalog.navigation.navigateToCatalogScreen
import ru.moonlight.feature_profile.navigation.navigateToProfileScreen
import ru.moonlight.mobile.navigation.TopLevelDestination
import ru.moonlight.network.utils.NetworkMonitor

@Composable
fun rememberMoonlightAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    //networkMonitor: NetworkMonitor,
): MoonlightAppState {
    return remember(
        navController,
        coroutineScope,
        //networkMonitor,
    ) {
        MoonlightAppState(
            navController = navController,
            coroutineScope = coroutineScope,
            //networkMonitor = networkMonitor
        )
    }
}

@Stable
class MoonlightAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    //networkMonitor: NetworkMonitor,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() {
            return TopLevelDestination.entries.firstOrNull { topLevelDestination ->
                currentDestination?.hasRoute(route = topLevelDestination.route) ?: false
            }
        }

//    val isOffline = networkMonitor.isOnline
//        .map(Boolean::not)
//        .stateIn(
//            scope = coroutineScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = false,
//        )

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }

            when (topLevelDestination) {
                TopLevelDestination.CATALOG -> navController.navigateToCatalogScreen(topLevelNavOptions)
                TopLevelDestination.CART -> navController.navigateToCartScreen(topLevelNavOptions)
                TopLevelDestination.PROFILE -> navController.navigateToProfileScreen(topLevelNavOptions)
            }
        }
    }
}