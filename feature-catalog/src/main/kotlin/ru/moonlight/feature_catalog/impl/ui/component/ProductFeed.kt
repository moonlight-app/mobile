package ru.moonlight.feature_catalog.impl.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import ru.moonlight.domain_model.catalog.CatalogProductDomainModel
import ru.moonlight.theme.MoonlightTheme
import scrollbar

@Composable
internal fun ProductFeed(
    onProductClick: (Int) -> Unit,
    productList: LazyPagingItems<CatalogProductDomainModel>,
    scrollState: LazyStaggeredGridState,
    modifier: Modifier = Modifier,
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier
            .fillMaxSize()
            .scrollbar(state = scrollState)
            .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges),
        columns = StaggeredGridCells.Fixed(2),
        state = scrollState,
        verticalItemSpacing = MoonlightTheme.dimens.paddingFromEdges,
        horizontalArrangement = Arrangement.spacedBy(MoonlightTheme.dimens.paddingFromEdges),
        content = {
            items(
                productList.itemCount,
                productList.itemKey { it.id }
            ) { item ->
                ProductItem(
                    onProductClick = onProductClick,
                    id = productList[item]!!.id,
                    title = productList[item]!!.title,
                    price = productList[item]!!.price.toString(),
                    imageUrl = productList[item]!!.previewUrl,
                )
            }

            when (productList.loadState.refresh) { //First load
                is LoadState.Error -> item { ErrorText() }
                is LoadState.Loading -> {
                    repeat(6) {
                        item { ShimmerProductItem() }
                    }
                }
                else -> {}
            }

            when (productList.loadState.append) {
                is LoadState.Error -> { item { ErrorText() } }
                is LoadState.Loading -> {
                    repeat(2) {
                        item { ShimmerProductItem() }
                    }
                }
                else -> {}
            }
        }
    )
}