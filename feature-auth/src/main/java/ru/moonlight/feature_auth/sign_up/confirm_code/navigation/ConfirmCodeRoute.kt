package ru.moonlight.feature_auth.sign_up.confirm_code.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ru.moonlight.feature_auth.sign_up.confirm_code.ConfirmCodeScreen

@Serializable
data object ConfirmCodeRoute

fun NavController.navigateToConfirmCode(navOptions: NavOptions? = null) = navigate(route = ConfirmCodeRoute, navOptions = navOptions)

fun NavGraphBuilder.confirmCodeScreen(
    onContinueClick: () -> Unit,
) {
    composable<ConfirmCodeRoute> {
        ConfirmCodeScreen(
            onContinueClick = onContinueClick
        )
    }
}