package ru.moonlight.feature_auth_signin.sign_in.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ru.moonlight.feature_auth_signin.sign_in.SignInScreen

@Serializable
data object SignInRoute

fun NavController.navigateToSignIn(navOptions: NavOptions? = null) = navigate(route = SignInRoute, navOptions = navOptions)

fun NavGraphBuilder.signInScreen(
    onAuthorizeClick: () -> Unit,
    onRegistrationClick: () -> Unit,
) {
    composable<SignInRoute> {
        SignInScreen(
            onAuthorizeClick = onAuthorizeClick,
            onRegistrationClick = onRegistrationClick,
        )
    }
}