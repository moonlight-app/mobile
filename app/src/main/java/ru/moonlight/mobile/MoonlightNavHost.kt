package ru.moonlight.mobile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.moonlight.feature_auth.sign_in.navigation.signInScreen
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
            onAuthorizeClick = {},
        )
        catalogScreen()
        cartScreen()
    }
}