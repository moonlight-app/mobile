package ru.moonlight.feature_auth_signup_confirmcode.api.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ru.moonlight.feature_auth_signup_confirmcode.impl.ui.ConfirmCodeRoute
import ru.moonlight.feature_auth_signup_registration.registration.api.navigation.presentation.RegistrationViewModel

@Serializable
data object ConfirmCodeRoute

fun NavController.navigateToConfirmCode(navOptions: NavOptions? = null) = navigate(route = ConfirmCodeRoute, navOptions = navOptions)

fun NavGraphBuilder.confirmCodeScreen(
    onContinueClick: () -> Unit,
    navController: NavController,
) {
    composable<ConfirmCodeRoute> { backStackEntry ->
        val viewModel: RegistrationViewModel = if (navController.previousBackStackEntry != null) hiltViewModel(
            navController.previousBackStackEntry!!
        ) else hiltViewModel()

        ConfirmCodeRoute(
            onContinueClick = onContinueClick,
            viewModel = viewModel,
        )
    }
}