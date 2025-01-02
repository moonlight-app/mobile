package ru.moonlight.feature_auth_signup_registration.registration.api.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ru.moonlight.feature_auth_signup_registration.registration.impl.ui.RegistrationRoute

@Serializable
data object RegistrationRoute

fun NavController.navigateToRegistration(navOptions: NavOptions? = null) = navigate(route = RegistrationRoute, navOptions = navOptions)

fun NavGraphBuilder.registrationScreen(
    onCreateAccountClick: (String, String, String, String, String) -> Unit,
) {
    composable<RegistrationRoute> {
        RegistrationRoute(
            onCreateAccountClick = onCreateAccountClick
        )
    }
}