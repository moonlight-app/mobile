package ru.moonlight.mobile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import ru.moonlight.feature_auth.sign_in.navigation.signInScreen
import ru.moonlight.feature_auth.sign_up.confirm_code.navigation.confirmCodeScreen
import ru.moonlight.feature_auth.sign_up.confirm_code.navigation.navigateToConfirmCode
import ru.moonlight.feature_auth.sign_up.registration.navigation.navigateToRegistration
import ru.moonlight.feature_auth.sign_up.registration.navigation.registrationScreen
import ru.moonlight.feature_auth.sign_up.registration_complete.navigation.navigateToRegistrationComplete
import ru.moonlight.feature_auth.sign_up.registration_complete.navigation.registrationCompleteScreen
import ru.moonlight.feature_cart.navigation.cartScreen
import ru.moonlight.feature_catalog.navigation.CatalogRoute
import ru.moonlight.feature_catalog.navigation.catalogScreen
import ru.moonlight.feature_catalog.navigation.navigateToCatalog
import ru.moonlight.feature_profile.navigation.navigateToProfileScreen
import ru.moonlight.feature_profile.navigation.profileScreen
import ru.moonlight.mobile.ui.MoonlightAppState

@Composable
fun MoonlightNavHost(
    modifier: Modifier = Modifier,
    appState: MoonlightAppState,
) {
    val navController: NavHostController = appState.navController

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = CatalogRoute,
    ) {
        catalogScreen()
        cartScreen()
        profileScreen(
            onLogoutClick = {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(CatalogRoute, inclusive = true)
                    .build()

                navController.navigateToCatalog(navOptions = navOptions)
            },
        )
        signInScreen(
            onAuthorizeClick = {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(CatalogRoute, inclusive = false)
                    .setLaunchSingleTop(true)
                    .setRestoreState(true)
                    .build()

                navController.navigateToProfileScreen(navOptions = navOptions)
            },
            onRegistrationClick = navController::navigateToRegistration,
        )
        registrationScreen(
            onCreateAccountClick = { name, sex, birthDate, email, password ->
                navController.navigateToConfirmCode(name, sex, birthDate, email, password)
            },
        )
        confirmCodeScreen(
            onContinueClick = {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(CatalogRoute, inclusive = false)
                    .setLaunchSingleTop(true)
                    .setRestoreState(true)
                    .build()

                navController.navigateToRegistrationComplete(navOptions)
            }
        )
        registrationCompleteScreen(
            onGetStartClick = {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(CatalogRoute, inclusive = false)
                    .setLaunchSingleTop(true)
                    .setRestoreState(true)
                    .build()

                navController.navigateToProfileScreen(navOptions = navOptions)
            }
        )
    }
}