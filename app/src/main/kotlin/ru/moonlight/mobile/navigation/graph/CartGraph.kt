package ru.moonlight.mobile.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import ru.moonlight.feature_cart.api.navigation.CartRoute
import ru.moonlight.feature_cart.api.navigation.cartScreen
import ru.moonlight.feature_catalog_product.api.navigation.navigateToProductDetails
import ru.moonlight.feature_catalog_product.api.navigation.productDetailsScreen
import ru.moonlight.mobile.navigation.TopLevelDestination
import ru.moonlight.mobile.ui.MoonlightAppState

@Serializable
data object CartGraph

fun NavGraphBuilder.cartGraph(appState: MoonlightAppState, navController: NavController) {
    navigation<CartGraph>(
        startDestination = CartRoute,
    ) {
        cartScreen(
            onBackClick = { appState.navigateToTopLevelDestination(TopLevelDestination.CatalogCategories) },
            onVisitCatalogClick = {
                appState.navigateToTopLevelDestination(TopLevelDestination.CatalogCategories)
            },
            onProductClick = { id -> navController.navigateToProductDetails(productId = id) },
            onSignInClick = {
                appState.navigateToTopLevelDestination(TopLevelDestination.Profile)
            },
            isUserAuthorize = appState.isUserAuthorize.value
        )

        productDetailsScreen(
            onBackClick = navController::popBackStack,
            onCartClick = navController::popBackStack,
            onAuthClick = {},
            isUserAuthorized = true,
        )
    }
}

fun NavController.navigateToCartGraph(navOptions: NavOptions? = null) =
    navigate(route = CartGraph, navOptions = navOptions)