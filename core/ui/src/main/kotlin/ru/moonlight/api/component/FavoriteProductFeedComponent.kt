package ru.moonlight.api.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import ru.moonlight.api.layout.GridLayout
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.utils.feed.isPagerEmpty
import ru.moonlight.api.widget.button.ButtonWidget
import ru.moonlight.api.widget.button.OutlinedButtonWidget
import ru.moonlight.api.widget.text.ButtonTextWidget
import ru.moonlight.api.widget.text.SecondTitleTextWidget
import ru.moonlight.api.widget.text.TextFieldTextWidget
import ru.moonlight.ui.R
import scrollbar

class FavoriteProductFeedModel(
    val id: Long,
    val title: String,
    val price: Double,
    val previewUrl: String,
    val isFavorite: Boolean,
)

@Composable
fun FavoriteProductFeedComponent(
    onProductClick: (id: Long) -> Unit,
    onFavouriteClick: (id: Long, currentStatus: Boolean) -> Unit,
    onVisitCatalogClick:() -> Unit,
    productFeedModelList: LazyPagingItems<FavoriteProductFeedModel>,
    scrollState: LazyStaggeredGridState,
    isFavoriteButtonVisible: Boolean,
    modifier: Modifier = Modifier,
) {
    when {
        productFeedModelList.loadState.refresh is LoadState.Loading -> {
            LoadingFeed()
        }
        productFeedModelList.loadState.refresh is LoadState.Error -> {
            ErrorFeed(
                onRepeatClick = productFeedModelList::refresh
            )
        }
        isPagerEmpty(productFeedModelList.itemCount) -> {
            EmptyFeed(
                modifier = modifier,
                onVisitCatalogClick = onVisitCatalogClick
            )
        }
        else -> {
            FilledFeed(
                modifier = modifier,
                onProductClick = onProductClick,
                onFavouriteClick = onFavouriteClick,
                productFeedModelList = productFeedModelList,
                scrollState = scrollState,
                isFavoriteButtonVisible = isFavoriteButtonVisible,
            )
        }
    }
}

@Composable
private fun LoadingFeed(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges),
    ) {
        GridLayout(
            items = listOf(1, 2, 3, 4, 5, 6),
            cellSize = 2,
            verticalItemSpacing = MoonlightTheme.dimens.paddingBetweenComponentsMediumVertical,
            horizontalItemSpacing = MoonlightTheme.dimens.paddingFromEdges,
        ) {
            SkeletonProductItem()
        }
    }
}

@Composable
private fun EmptyFeed(
    onVisitCatalogClick:() -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = MoonlightTheme.dimens.paddingBetweenComponentsMediumVertical,
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SecondTitleTextWidget(text = stringResource(R.string.nothingThere))
            TextFieldTextWidget(text = stringResource(R.string.youDidntAddAnythingYet))
        }

        ButtonWidget(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal)
                .padding(bottom = MoonlightTheme.dimens.paddingFromEdges),
            onClick = onVisitCatalogClick,
            text = stringResource(R.string.visit_catalog),
        )
    }
}

@Composable
private fun FilledFeed(
    onProductClick: (Long) -> Unit,
    onFavouriteClick: (Long, Boolean) -> Unit,
    productFeedModelList: LazyPagingItems<FavoriteProductFeedModel>,
    scrollState: LazyStaggeredGridState,
    isFavoriteButtonVisible: Boolean,
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
                    modifier = Modifier
                        .animateItem(fadeInSpec = null, fadeOutSpec = null),
                    onProductClick = onProductClick,
                    onFavouriteClick = onFavouriteClick,
                    id = productFeedModelList[item]!!.id,
                    title = productFeedModelList[item]!!.title,
                    price = productFeedModelList[item]!!.price.toString(),
                    imageUrl = productFeedModelList[item]!!.previewUrl,
                    isFavorite = productFeedModelList[item]!!.isFavorite,
                    isFavoriteButtonVisible = isFavoriteButtonVisible,
                )
            }

            when (productFeedModelList.loadState.append) {
                is LoadState.Error -> {
                    item {
                        ErrorFeed(
                            onRepeatClick = productFeedModelList::retry
                        )
                    }
                }
                is LoadState.Loading -> {
                    repeat(2) {
                        item { SkeletonProductItem() }
                    }
                }
                else -> {}
            }
        }
    )
}

@Composable
private fun ErrorFeed(
    onRepeatClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            space = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ButtonTextWidget(
            text = stringResource(R.string.somethingWentWrong),
        )
        OutlinedButtonWidget(
            onClick = onRepeatClick,
            text = stringResource(R.string.repeat),
        )
    }
}