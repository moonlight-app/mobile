package ru.moonlight.feature_profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ru.moonlight.feature_profile.ProfileScreen

@Serializable
data object ProfileRoute

fun NavController.navigateToProfile(navOptions: NavOptions? = null) = navigate(route = ProfileRoute, navOptions = navOptions)

fun NavGraphBuilder.profileScreen(
    onLogoutClick: () -> Unit,
    onEditProfileClick: (String, String, String) -> Unit,
    onOrderClick: () -> Unit,
    onFavoritesClick: () -> Unit
) {
    composable<ProfileRoute> {
        ProfileScreen(
            onLogoutClick = onLogoutClick,
            onEditProfileClick = onEditProfileClick,
            onOrderClick = onOrderClick,
            onFavoritesClick = onFavoritesClick,
        )
    }
}
