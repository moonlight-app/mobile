package ru.moonlight.api.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import ru.moonlight.api.layout.GridLayout
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.utils.feed.isPagerEmpty
import ru.moonlight.api.widget.button.ButtonWidget
import ru.moonlight.api.widget.button.OutlinedButtonWidget
import ru.moonlight.api.widget.text.ButtonTextWidget
import ru.moonlight.api.widget.text.SecondTitleTextWidget
import ru.moonlight.api.widget.text.TextFieldTextWidget
import ru.moonlight.ui.R

@Composable
fun OrderFeedComponent(
    onProductClick:(Long) -> Unit,
    onVisitCatalogClick:() -> Unit,
    orderFeedModelList: LazyPagingItems<OrderUiModel>,
    modifier: Modifier = Modifier,
) {
    when {
        orderFeedModelList.loadState.refresh is LoadState.Loading -> {
            LoadingFeed()
        }
        orderFeedModelList.loadState.refresh is LoadState.Error -> {
            ErrorFeed(
                onRepeatClick = orderFeedModelList::refresh
            )
        }
        isPagerEmpty(orderFeedModelList.itemCount) -> {
            EmptyFeed(
                modifier = modifier,
                onVisitCatalogClick = onVisitCatalogClick,
            )
        }
        else -> {
            FilledFeed(
                modifier = modifier,
                onProductClick = onProductClick,
                orderFeedModelList = orderFeedModelList,
            )
        }
    }
}



@Composable
private fun LoadingFeed(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        GridLayout(
            items = listOf(1, 2, 3),
            cellSize = 1,
        ) {
            SkeletonCartItem()
        }
    }
}

@Composable
private fun EmptyFeed(
    onVisitCatalogClick: () -> Unit,
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
            SecondTitleTextWidget(text = stringResource(R.string.empty_cart))
            TextFieldTextWidget(text = stringResource(R.string.take_a_look_at_catalog))
        }

        ButtonWidget(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal)
                .padding(bottom = MoonlightTheme.dimens.paddingFromEdges),
            onClick = onVisitCatalogClick,
            text = stringResource(R.string.visit_catalog)
        )
    }
}

@Composable
private fun FilledFeed(
    onProductClick:(Long) -> Unit,
    orderFeedModelList: LazyPagingItems<OrderUiModel>,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(
            space = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
        ),
    ) {
        items(orderFeedModelList.itemSnapshotList.items) { cartModel ->
            OrderFeedItemComponent(
                modifier = modifier
                    .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
                onProductClick = onProductClick,
                id = cartModel.id,
                title = cartModel.title,
                type = cartModel.type,
                imageUrl = cartModel.imageUrl,
                price = cartModel.price,
                size = cartModel.size,
                status = cartModel.status.convertStatusToUiModel(context = context),
            )
        }

        when (orderFeedModelList.loadState.append) {
            is LoadState.Error -> {
                item {
                    ErrorFeed(
                        onRepeatClick = orderFeedModelList::retry
                    )
                }
            }

            is LoadState.Loading -> {
                repeat(2) {
                    item { SkeletonCartItem() }
                }
            }

            else -> {}
        }
    }
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