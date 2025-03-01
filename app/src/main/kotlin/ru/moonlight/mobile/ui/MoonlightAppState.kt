package ru.moonlight.mobile.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.moonlight.mobile.navigation.PreTopLevelDestination
import ru.moonlight.mobile.navigation.TopLevelDestination
import ru.moonlight.mobile.navigation.graph.navigateToAuthGraph
import ru.moonlight.mobile.navigation.graph.navigateToCartGraph
import ru.moonlight.mobile.navigation.graph.navigateToCatalogGraph
import ru.moonlight.mobile.navigation.graph.navigateToProfileGraph
import ru.moonlight.network.utils.NetworkMonitor

@Composable
fun rememberMoonlightAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    networkMonitor: NetworkMonitor,
    isUserAuthorize: Flow<Boolean>,
): MoonlightAppState {
    val localIsUserAuthorize = isUserAuthorize.collectAsState(initial = false)

    return remember(
        navController,
        coroutineScope,
        networkMonitor,
    ) {
        MoonlightAppState(
            navController = navController,
            coroutineScope = coroutineScope,
            networkMonitor = networkMonitor,
            isUserAuthorize = localIsUserAuthorize,
        )
    }
}

@Stable
class MoonlightAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
    val isUserAuthorize: State<Boolean>,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val isCurrentTopLevelDestination: Boolean
        @Composable get() {
            val currentDestination = currentDestination
            return currentDestination != null &&
                    TopLevelDestination.all.any { topLevelDestination ->
                        currentDestination.hasRoute(topLevelDestination.route)
                    }
        }

    val isCurrentPreTopLevelDestination: Boolean
        @Composable get() {
            val currentDestination = currentDestination
            return currentDestination != null &&
                    PreTopLevelDestination.all.any { preTopLevelDestination ->
                        currentDestination.hasRoute(preTopLevelDestination.route)
                    }
        }

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.all

    val localIsUserAuthorized
        @Composable get() = remember { isUserAuthorize }

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.route}") {
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            when (topLevelDestination) {
                TopLevelDestination.CatalogCategories -> {
                    navController.navigateToCatalogGraph(navOptions = topLevelNavOptions)
                }
                TopLevelDestination.Cart -> navController.navigateToCartGraph(navOptions = topLevelNavOptions)
                TopLevelDestination.Profile -> {
                    if (isUserAuthorize.value) navController.navigateToProfileGraph(navOptions = topLevelNavOptions)
                    else navController.navigateToAuthGraph()
                }
            }
        }
    }
}