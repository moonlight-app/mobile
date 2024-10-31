package ru.moonlight.mobile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.moonlight.feature_auth.sign_in.navigation.SignInRoute
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

@Composable
fun MoonlightNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = CatalogRoute,
    ) {
        signInScreen(
            onAuthorizeClick = { navController.navigateUp() },
            onRegistrationClick = { navController.navigateToRegistration() },
        )
        registrationScreen(
            onCreateAccountClick = { navController.navigateToConfirmCode() },
        )
        confirmCodeScreen(
            onContinueClick = { navController.navigateToRegistrationComplete() }
        )
        registrationCompleteScreen(
            onGetStartClick = {  } //TODO Add update profileScreen logic
        )
        catalogScreen()
        cartScreen()
    }
}