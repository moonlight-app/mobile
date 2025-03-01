package ru.moonlight.mobile.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import ru.moonlight.feature_auth_signin.sign_in.api.navigation.SignInRoute
import ru.moonlight.feature_auth_signin.sign_in.api.navigation.signInScreen
import ru.moonlight.feature_auth_signup_complete.api.navigation.navigateToRegistrationComplete
import ru.moonlight.feature_auth_signup_complete.api.navigation.registrationCompleteScreen
import ru.moonlight.feature_auth_signup_confirmcode.api.navigation.confirmCodeScreen
import ru.moonlight.feature_auth_signup_confirmcode.api.navigation.navigateToConfirmCode
import ru.moonlight.feature_auth_signup_registration.registration.api.navigation.navigateToRegistration
import ru.moonlight.feature_auth_signup_registration.registration.api.navigation.registrationScreen
import ru.moonlight.feature_cart.api.navigation.CartRoute
import ru.moonlight.feature_catalog_product.api.navigation.ProductDetailsRoute
import ru.moonlight.mobile.navigation.TopLevelDestination
import ru.moonlight.mobile.ui.MoonlightAppState

@Serializable
data object AuthGraph

fun NavGraphBuilder.authGraph(
    appState: MoonlightAppState,
    navController: NavController,
) {
    navigation<AuthGraph>(
        startDestination = SignInRoute,
    ) {
        signInScreen(
            onAuthorizeClick = {
                if (navController.previousBackStackEntry?.destination?.hasRoute<ProductDetailsRoute>() == true) {
                    navController.popBackStack()
                } else if ( navController.previousBackStackEntry?.destination?.hasRoute<CartRoute>() == true) {
                    navController.popBackStack()
                } else {
                    appState.navigateToTopLevelDestination(TopLevelDestination.Profile)
                }
            },
            onRegistrationClick = navController::navigateToRegistration,
        )

        registrationScreen(
            onCreateAccountClick = { navController.navigateToConfirmCode() },
        )

        confirmCodeScreen(
            onContinueClick = {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(CatalogGraph, inclusive = false) // сохраняем CatalogGraph
                    .setLaunchSingleTop(true)
                    .setRestoreState(true)
                    .build()

                navController.navigateToRegistrationComplete(navOptions)
            },
            navController = navController,
        )

        registrationCompleteScreen(
            onGetStartClick = {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(CatalogGraph, inclusive = false) // сохраняем CatalogGraph
                    .setLaunchSingleTop(true)
                    .setRestoreState(true)
                    .build()

                navController.navigateToProfileGraph(navOptions)
            }
        )
    }
}

fun NavController.navigateToAuthGraph(navOptions: NavOptions? = null) =
    navigate(route = AuthGraph, navOptions = navOptions)
