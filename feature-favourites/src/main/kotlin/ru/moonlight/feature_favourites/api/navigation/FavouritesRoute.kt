package ru.moonlight.feature_favourites.api.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ru.moonlight.feature_favourites.impl.ui.FavouritesRoute


@Serializable
data object FavouritesRoute

fun NavController.navigateToFavouritesScreen(navOptions: NavOptions? = null) = navigate(route = FavouritesRoute, navOptions = navOptions)

fun NavGraphBuilder.favouritesScreen(
    onBackClick: () -> Unit,
    onProductClick: (Long) -> Unit,
    onVisitCatalogClick: () -> Unit,
) {
    composable<FavouritesRoute> {
        FavouritesRoute(
            onBackClick = onBackClick,
            onProductClick = onProductClick,
            onVisitCatalogClick = onVisitCatalogClick,
        )
    }
}