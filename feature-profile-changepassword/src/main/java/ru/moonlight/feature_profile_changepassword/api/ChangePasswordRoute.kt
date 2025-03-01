package ru.moonlight.feature_profile_changepassword.api

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ru.moonlight.feature_profile_changepassword.impl.ui.ChangePasswordRoute

@Serializable
object ChangePasswordRoute

fun NavController.navigateToChangePassword(
    navOptions: NavOptions? = null,
) = navigate(route = ChangePasswordRoute, navOptions = navOptions)

fun NavGraphBuilder.changePasswordScreen(
    onBackClick: () -> Unit,
) {
    composable<ChangePasswordRoute> {
        ChangePasswordRoute(
            onBackClick = onBackClick,
        )
    }
}