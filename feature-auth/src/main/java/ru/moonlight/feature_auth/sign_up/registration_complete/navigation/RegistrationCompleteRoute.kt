package ru.moonlight.feature_auth.sign_up.registration_complete.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ru.moonlight.feature_auth.sign_up.registration_complete.RegistrationCompleteScreen

@Serializable
data object RegistrationCompleteRoute

fun NavController.navigateToRegistrationComplete(navOptions: NavOptions? = null) = navigate(route = RegistrationCompleteRoute, navOptions = navOptions)

fun NavGraphBuilder.registrationCompleteScreen(
    onGetStartClick: () -> Unit,
) {
    composable<RegistrationCompleteRoute> {
        RegistrationCompleteScreen(
            onGetStartClick = onGetStartClick,
        )
    }
}

