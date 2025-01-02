package ru.moonlight.feature_catalog_categories.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.moonlight.api.component.CategoryButtonComponent
import ru.moonlight.api.component.TopAppBarComponent
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.feature_catalog_categories.R

@Composable
internal fun CatalogCategoriesRoute(
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    CatalogCategoriesScreen(
        modifier = modifier,
        onCategoryClick = onCategoryClick
    )
}

@Composable
private fun CatalogCategoriesScreen(
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val categoryList = LocalContext.current.categories

    Scaffold(
        modifier = modifier,
        containerColor = MoonlightTheme.colors.background,
        topBar = {
            TopAppBarComponent(
                title = stringResource(R.string.catalog),
            )
        }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(top = paddingValues.calculateTopPadding()),
        ) {
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = MoonlightTheme.dimens.paddingFromEdges,
                horizontalArrangement = Arrangement.spacedBy(MoonlightTheme.dimens.paddingFromEdges - 5.dp),
                content = {
                    items(categoryList) { category ->
                        CategoryButtonComponent(
                            onCategoryClick = onCategoryClick,
                            category.title,
                            category.productType,
                            category.image,
                        )
                    }
                },
            )
        }
    }
}