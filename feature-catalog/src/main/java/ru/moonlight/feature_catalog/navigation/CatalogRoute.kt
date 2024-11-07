package ru.moonlight.feature_catalog.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ru.moonlight.feature_catalog.CatalogScreen

@Serializable
data object CatalogRoute

fun NavController.navigateToCatalog(navOptions: NavOptions? = null) = navigate(route = CatalogRoute, navOptions = navOptions)

fun NavGraphBuilder.catalogScreen() {
    composable<CatalogRoute> {
        CatalogScreen()
    }
}