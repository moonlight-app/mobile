package ru.moonlight.feature_profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ru.moonlight.feature_profile.ProfileScreen

@Serializable
data object ProfileRoute

fun NavController.navigateToProfileScreen(navOptions: NavOptions? = null) = navigate(route = ProfileRoute, navOptions = navOptions)

fun NavGraphBuilder.profileScreen() {
    composable<ProfileRoute> {
        ProfileScreen()
    }
}
