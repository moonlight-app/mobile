package ru.moonlight.feature_profile.api.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ru.moonlight.feature_profile.impl.ui.ProfileRoute

@Serializable
data object ProfileRoute

fun NavController.navigateToProfile(navOptions: NavOptions? = null) = navigate(route = ProfileRoute, navOptions = navOptions)

fun NavGraphBuilder.profileScreen(
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onEditProfileClick: (String, String, String) -> Unit,
    onOrderClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
) {
    composable<ProfileRoute> {
        ProfileRoute(
            onBackClick = onBackClick,
            onLogoutClick = onLogoutClick,
            onEditProfileClick = onEditProfileClick,
            onOrderClick = onOrderClick,
            onFavoritesClick = onFavoritesClick,
            onChangePasswordClick = onChangePasswordClick,
        )
    }
}
