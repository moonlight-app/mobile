package ru.moonlight.mobile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import ru.moonlight.feature_cart.api.navigation.CartRoute
import ru.moonlight.feature_catalog.api.navigation.CatalogRoute
import ru.moonlight.feature_catalog_categories.api.navigation.CatalogCategoriesRoute
import ru.moonlight.feature_profile.api.navigation.ProfileRoute
import kotlin.reflect.KClass

sealed class TopLevelDestination(
    val icon: @Composable () -> ImageVector,
    val route: KClass<*>,
    val badgeCount: Int? = null
) {
    object CatalogCategories : TopLevelDestination(
        icon = { ImageVector.vectorResource(ru.moonlight.ui.R.drawable.catalog) },
        route = CatalogCategoriesRoute::class
    )

    object Cart : TopLevelDestination(
        icon = { ImageVector.vectorResource(ru.moonlight.ui.R.drawable.cart) },
        route = CartRoute::class,
        badgeCount = null
    )

    object Profile : TopLevelDestination(
        icon = { ImageVector.vectorResource(ru.moonlight.ui.R.drawable.profile) },
        route = ProfileRoute::class
    )

    companion object {
        val all = listOf(CatalogCategories, Cart, Profile)
    }
}

sealed class PreTopLevelDestination(val route: KClass<*>) {
    object Catalog : PreTopLevelDestination(CatalogRoute::class)

    companion object {
        val all = listOf(Catalog)
    }
}



