package ru.moonlight.feature_catalog_categories.api.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ru.moonlight.feature_catalog_categories.impl.CatalogCategoriesRoute

@Serializable
data object CatalogCategoriesRoute

fun NavController.navigateToCatalogCategories(navOptions: NavOptions? = null) = navigate(route = CatalogCategoriesRoute, navOptions = navOptions)

fun NavGraphBuilder.catalogCategoriesScreen(
    onCategoryClick: (String) -> Unit,
) {
    composable<CatalogCategoriesRoute> {
        CatalogCategoriesRoute(
            onCategoryClick = onCategoryClick
        )
    }
}
