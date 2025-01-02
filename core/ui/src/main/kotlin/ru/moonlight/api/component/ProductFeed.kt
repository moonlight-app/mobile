package ru.moonlight.api.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.text.ButtonTextWidget
import ru.moonlight.ui.R
import scrollbar

class ProductFeedModel(
    val id: Int,
    val title: String,
    val price: Double,
    val previewUrl: String,
    val isFavorite: Boolean,
)

@Composable
fun ProductFeed(
    onProductClick: (Int) -> Unit,
    productFeedModelList: LazyPagingItems<ProductFeedModel>,
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
                count = productFeedModelList.itemCount,
                key = productFeedModelList.itemKey { it.id },
                contentType = productFeedModelList.itemContentType()
            ) { item ->
                ProductItemComponent(
                    onProductClick = onProductClick,
                    id = productFeedModelList[item]!!.id,
                    title = productFeedModelList[item]!!.title,
                    price = productFeedModelList[item]!!.price.toString(),
                    imageUrl = productFeedModelList[item]!!.previewUrl,
                )
            }

            when (productFeedModelList.loadState.refresh) { //First load
                is LoadState.Error -> item { ErrorText() }
                is LoadState.Loading -> {
                    repeat(6) {
                        item { ShimmerProductItem() }
                    }
                }
                else -> {}
            }

            when (productFeedModelList.loadState.append) {
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

@Composable
private fun ErrorText(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        ButtonTextWidget(
            text = stringResource(R.string.somethingWentWrong),
        )
    }
}