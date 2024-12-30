package ru.moonlight.mobile.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import ru.moonlight.feature_cart.navigation.CartRoute
import ru.moonlight.feature_catalog_categories.navigation.CatalogCategoriesRoute
import ru.moonlight.feature_profile.navigation.ProfileRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null,
    val route: KClass<*>,
) {
    CATALOG_CATEGORIES(
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        route = CatalogCategoriesRoute::class,
    ),
    CART(
        selectedIcon = Icons.Filled.ShoppingCart,
        unselectedIcon = Icons.Outlined.ShoppingCart,
        route = CartRoute::class,
        badgeCount = 2
    ),
    PROFILE(
        selectedIcon = Icons.Filled.Face,
        unselectedIcon = Icons.Outlined.Face,
        route = ProfileRoute::class,
    ),
}