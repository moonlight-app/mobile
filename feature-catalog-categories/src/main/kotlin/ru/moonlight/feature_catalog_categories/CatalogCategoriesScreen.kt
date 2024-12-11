package ru.moonlight.feature_catalog_categories

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.moonlight.theme.MoonlightTheme
import ru.moonlight.ui.TopAppBarComponent
import ru.moonlight.utils.bouncingClickable

@Composable
fun CatalogCategoriesScreen(
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    CatalogCategoriesView(
        modifier = modifier,
        onCategoryClick = onCategoryClick
    )
}

@Composable
private fun CatalogCategoriesView(
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
                .padding(paddingValues),
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
                        CategoryItem(
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

@Composable
fun CategoryItem(
    onCategoryClick: (String) -> Unit,
    title: String,
    productType: String,
    @DrawableRes image: Int,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .bouncingClickable {
                onCategoryClick(productType)
            },
        shape = MoonlightTheme.shapes.buttonShape,
        border = BorderStroke(1.dp, color = MoonlightTheme.colors.disabledComponent),
        colors = CardDefaults.cardColors(
            containerColor = MoonlightTheme.colors.card2,
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            Text(
                modifier = Modifier
                    .padding(MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
                text = title,
                style = MoonlightTheme.typography.subTitle,
                color = MoonlightTheme.colors.text,
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Inside,
                )
            }
        }
    }
}
