package ru.moonlight.mobile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import kotlinx.serialization.Serializable
import ru.moonlight.mobile.navigation.graph.CatalogGraph
import ru.moonlight.mobile.navigation.graph.authGraph
import ru.moonlight.mobile.navigation.graph.cartGraph
import ru.moonlight.mobile.navigation.graph.catalogGraph
import ru.moonlight.mobile.navigation.graph.profileGraph
import ru.moonlight.mobile.ui.MoonlightAppState

@Serializable
data object MoonlightRootGraph

@Composable
fun MoonlightNavHost(
    modifier: Modifier = Modifier,
    appState: MoonlightAppState,
) {
    val navController: NavHostController = appState.navController
    val userAuth = appState.localIsUserAuthorized.value

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = CatalogGraph,
        route = MoonlightRootGraph::class,
    ) {
        catalogGraph(appState, navController)
        cartGraph(appState, navController)
        profileGraph(appState, navController)
        authGraph(appState, navController)
    }
}