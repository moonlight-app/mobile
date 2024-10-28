package ru.moonlight.feature_cart.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ru.moonlight.feature_cart.CartScreen

@Serializable
data object CartRoute

fun NavController.navigateToCartScreen(navOptions: NavOptions? = null) = navigate(route = CartRoute, navOptions = navOptions)

fun NavGraphBuilder.cartScreen() {
    composable<CartRoute> {
        CartScreen()
    }
}