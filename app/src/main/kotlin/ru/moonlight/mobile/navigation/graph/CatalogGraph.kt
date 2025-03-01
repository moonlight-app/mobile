package ru.moonlight.mobile.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import ru.moonlight.feature_catalog.api.navigation.catalogScreen
import ru.moonlight.feature_catalog.api.navigation.navigateToCatalog
import ru.moonlight.feature_catalog_categories.api.navigation.CatalogCategoriesRoute
import ru.moonlight.feature_catalog_categories.api.navigation.catalogCategoriesScreen
import ru.moonlight.feature_catalog_product.api.navigation.navigateToProductDetails
import ru.moonlight.feature_catalog_product.api.navigation.productDetailsScreen
import ru.moonlight.mobile.navigation.TopLevelDestination
import ru.moonlight.mobile.ui.MoonlightAppState

@Serializable
data object CatalogGraph

fun NavGraphBuilder.catalogGraph(appState: MoonlightAppState, navController: NavController) {
    navigation<CatalogGraph> (
        startDestination = CatalogCategoriesRoute,
    ) {
        catalogCategoriesScreen(
            onCategoryClick = { category ->
                val navOptions = navOptions {
                    launchSingleTop = true
                    restoreState = true
                }

                navController.navigateToCatalog(category = category, navOptions = navOptions)
            }
        )

        catalogScreen(
            onBackClick = navController::popBackStack,
            onProductClick = { id -> navController.navigateToProductDetails(id) },
            isUserAuthorized = appState.isUserAuthorize.value,
        )

        productDetailsScreen(
            onBackClick = navController::popBackStack,
            onCartClick = { appState.navigateToTopLevelDestination(TopLevelDestination.Cart) },
            onAuthClick = { appState.navigateToTopLevelDestination(TopLevelDestination.Profile) },
            isUserAuthorized = appState.isUserAuthorize.value
        )
    }
}

fun NavController.navigateToCatalogGraph(navOptions: NavOptions? = null) =
    navigate(route = CatalogGraph, navOptions = navOptions)