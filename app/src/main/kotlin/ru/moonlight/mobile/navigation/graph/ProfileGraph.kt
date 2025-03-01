package ru.moonlight.mobile.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import ru.moonlight.feature_catalog_product.api.navigation.navigateToProductDetails
import ru.moonlight.feature_catalog_product.api.navigation.productDetailsScreen
import ru.moonlight.feature_favourites.api.navigation.favouritesScreen
import ru.moonlight.feature_favourites.api.navigation.navigateToFavouritesScreen
import ru.moonlight.feature_order.api.navigation.navigateToOrderScreen
import ru.moonlight.feature_order.api.navigation.orderScreen
import ru.moonlight.feature_profile.api.navigation.ProfileRoute
import ru.moonlight.feature_profile.api.navigation.profileScreen
import ru.moonlight.feature_profile_changepassword.api.changePasswordScreen
import ru.moonlight.feature_profile_changepassword.api.navigateToChangePassword
import ru.moonlight.feature_profile_edit.api.navigation.navigateToProfileEdit
import ru.moonlight.feature_profile_edit.api.navigation.profileEditScreen
import ru.moonlight.mobile.navigation.TopLevelDestination
import ru.moonlight.mobile.ui.MoonlightAppState

@Serializable
data object ProfileGraph

fun NavGraphBuilder.profileGraph(appState: MoonlightAppState, navController: NavController) {
    navigation<ProfileGraph>(
        startDestination = ProfileRoute
    ) {
        profileScreen(
            onBackClick = { appState.navigateToTopLevelDestination(TopLevelDestination.CatalogCategories) },
            onLogoutClick = {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(CatalogGraph, inclusive = true)
                    .build()

                navController.navigateToCatalogGraph(navOptions = navOptions)
            },
            onEditProfileClick = { name, sex, birthDate ->
                navController.navigateToProfileEdit(name, sex, birthDate)
            },
            onOrderClick = navController::navigateToOrderScreen,
            onFavoritesClick = navController::navigateToFavouritesScreen,
            onChangePasswordClick = navController::navigateToChangePassword,
        )

        profileEditScreen(onBackClick = navController::popBackStack)

        changePasswordScreen(onBackClick = navController::popBackStack)

        orderScreen(
            onBackClick = navController::popBackStack,
            onProductClick = { id -> navController.navigateToProductDetails(productId = id) },
            onVisitCatalogClick = { appState.navigateToTopLevelDestination(topLevelDestination = TopLevelDestination.CatalogCategories) },
        )

        favouritesScreen(
            onBackClick = navController::popBackStack,
            onProductClick = { id -> navController.navigateToProductDetails(productId = id) },
            onVisitCatalogClick = { appState.navigateToTopLevelDestination(topLevelDestination = TopLevelDestination.CatalogCategories) },
        )

        productDetailsScreen(
            onBackClick = navController::popBackStack,
            onCartClick = { appState.navigateToTopLevelDestination(TopLevelDestination.Cart) },
            onAuthClick = {},
            isUserAuthorized = true,
        )

    }
}

fun NavController.navigateToProfileGraph(navOptions: NavOptions? = null) =
    navigate(route = ProfileGraph, navOptions = navOptions)