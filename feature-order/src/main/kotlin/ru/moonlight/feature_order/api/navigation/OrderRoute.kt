package ru.moonlight.feature_order.api.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ru.moonlight.feature_order.impl.ui.OrderRoute


@Serializable
data object OrderRoute

fun NavController.navigateToOrderScreen(navOptions: NavOptions? = null) = navigate(route = OrderRoute, navOptions = navOptions)

fun NavGraphBuilder.orderScreen(
    onBackClick: () -> Unit,
    onProductClick: (Long) -> Unit,
    onVisitCatalogClick: () -> Unit,
) {
    composable<OrderRoute> {
        OrderRoute(
            onBackClick = onBackClick,
            onProductClick = onProductClick,
            onVisitCatalogClick = onVisitCatalogClick,
        )
    }
}