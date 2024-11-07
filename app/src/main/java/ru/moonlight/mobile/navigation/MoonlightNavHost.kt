package ru.moonlight.mobile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.moonlight.feature_auth.sign_in.navigation.navigateToSignIn
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
            onLogoutClick = {appState.navigateToTopLevelDestination(TopLevelDestination.CATALOG)},
            onSignInClick = navController::navigateToSignIn,
            isUserAuthorize = appState.isUserAuthorized
        )
        signInScreen(
            onAuthorizeClick = {
                navController.navigateToProfileScreen()
            },
            onRegistrationClick = navController::navigateToRegistration,
        )
        registrationScreen(
            onCreateAccountClick = navController::navigateToConfirmCode,
        )
        confirmCodeScreen(
            onContinueClick = navController::navigateToRegistrationComplete
        )
        registrationCompleteScreen(
            onGetStartClick = {  } //TODO Add update profileScreen logic
        )
    }
}