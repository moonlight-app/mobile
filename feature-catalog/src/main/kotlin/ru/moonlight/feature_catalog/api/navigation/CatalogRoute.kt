package ru.moonlight.feature_catalog.api.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import ru.moonlight.feature_catalog.impl.ui.CatalogScreen

@Serializable
data class CatalogRoute(val category: String)

fun NavController.navigateToCatalog(
    category: String,
    navOptions: NavOptions? = null,
) = navigate(route = CatalogRoute(category), navOptions = navOptions)

fun NavGraphBuilder.catalogScreen(
    onBackClick: () -> Unit,
    onProductClick: (Int) -> Unit,
) {
    composable<CatalogRoute> {
        val args = it.toRoute<CatalogRoute>()
        CatalogScreen(
            productType = args.category,
            onBackClick = onBackClick,
            onProductClick = onProductClick,
        )
    }
}