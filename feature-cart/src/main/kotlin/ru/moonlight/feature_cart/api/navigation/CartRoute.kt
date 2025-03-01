package ru.moonlight.feature_cart.api.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ru.moonlight.feature_cart.impl.ui.CartRoute

@Serializable
data object CartRoute

fun NavController.navigateToCartScreen(navOptions: NavOptions? = null) = navigate(route = CartRoute, navOptions = navOptions)

fun NavGraphBuilder.cartScreen(
    onBackClick: () -> Unit,
    onVisitCatalogClick: () -> Unit,
    onProductClick: (Long) -> Unit,
    onSignInClick: () -> Unit,
    isUserAuthorize: Boolean,
) {
    composable<CartRoute> {
        CartRoute(
            onBackClick = onBackClick,
            onVisitCatalogClick = onVisitCatalogClick,
            onProductClick = onProductClick,
            onSignInClick = onSignInClick,
            isUserAuthorize = isUserAuthorize,
        )
    }
}