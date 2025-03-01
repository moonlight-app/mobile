package ru.moonlight.feature_catalog_product.api.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import ru.moonlight.feature_catalog_product.impl.ui.ProductDetailsRoute

@Serializable
data class ProductDetailsRoute(val productId: Long)

fun NavController.navigateToProductDetails(
    productId: Long,
    navOptions: NavOptions? = null
) = navigate(route = ProductDetailsRoute(productId), navOptions = navOptions)

fun NavGraphBuilder.productDetailsScreen(
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    onAuthClick: () -> Unit,
    isUserAuthorized: Boolean,
) {
    composable<ProductDetailsRoute> {
        val args = it.toRoute<ProductDetailsRoute>()
        ProductDetailsRoute(
            onBackClick = onBackClick,
            onCartClick = onCartClick,
            onAuthClick = onAuthClick,
            isUserAuthorized = isUserAuthorized,
            productId = args.productId,
        )
    }
}