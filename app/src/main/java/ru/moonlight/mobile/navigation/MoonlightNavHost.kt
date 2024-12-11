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
import ru.moonlight.feature_catalog_categories.navigation.CatalogCategoriesRoute
import ru.moonlight.feature_catalog_categories.navigation.catalogCategoriesScreen
import ru.moonlight.feature_catalog_categories.navigation.navigateToCatalogCategories
import ru.moonlight.feature_profile.navigation.navigateToProfile
import ru.moonlight.feature_profile.navigation.profileScreen
import ru.moonlight.feature_profile_edit.navigation.navigateToProfileEdit
import ru.moonlight.feature_profile_edit.navigation.profileEditScreen
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
        startDestination = CatalogCategoriesRoute,
        //startDestination = CatalogRoute,
    ) {
        /* Profile */
        profileScreen(
            onLogoutClick = {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(CatalogCategoriesRoute, inclusive = true)
                    .build()

                navController.navigateToCatalogCategories(navOptions = navOptions)
            },
            onEditProfileClick = { name, sex, birthDate ->
                navController.navigateToProfileEdit(name, sex, birthDate)
            },
            onOrderClick = {},
            onFavoritesClick = {},
        )

        profileEditScreen(onBackClick = navController::popBackStack)

        /* Auth */
        signInScreen(
            onAuthorizeClick = {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(CatalogCategoriesRoute, inclusive = false)
                    .setLaunchSingleTop(true)
                    .setRestoreState(true)
                    .build()

                navController.navigateToProfile(navOptions = navOptions)
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

                navController.navigateToProfile(navOptions = navOptions)
            }
        )

        /* Catalog */
        catalogCategoriesScreen(
            onCategoryClick = { category ->
                navController.navigateToCatalog(category = category)
            }
        )
        catalogScreen()

        /* Cart */
        cartScreen()
    }
}