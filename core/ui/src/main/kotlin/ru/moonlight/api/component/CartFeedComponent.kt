package ru.moonlight.api.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import ru.moonlight.api.layout.GridLayout
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.utils.feed.isPagerEmpty
import ru.moonlight.api.widget.button.ButtonWidget
import ru.moonlight.api.widget.button.OutlinedButtonWidget
import ru.moonlight.api.widget.text.ButtonTextWidget
import ru.moonlight.api.widget.text.SecondTitleTextWidget
import ru.moonlight.api.widget.text.SubTitleTextWidget
import ru.moonlight.api.widget.text.TextFieldTextWidget
import ru.moonlight.ui.R

class CartFeedModel(
    val itemId: Long,
    val productId: Long,
    val imageUrl: String,
    val price: String,
    val size: String?,
    val count: Int,
    val isFavourite: Boolean,
    val chosen: Boolean,
)

@Composable
fun CartFeedComponent(
    onProductClick: (id: Long) -> Unit,
    onCheckChange: (Long, Boolean) -> Unit,
    onDeleteClick: (Long) -> Unit,
    onFavouriteClick: (id: Long, currentStatus: Boolean) -> Unit,
    onVisitCatalogClick: () -> Unit,
    onOrderClick: () -> Unit,
    onIncreaseProductCountClick:(id: Long, count: Int) -> Unit,
    onDecreaseProductCountClick:(id: Long, count: Int) -> Unit,
    onSharedCheckBoxClick:(Boolean) -> Unit,
    isCheckSharedCheckBox: Boolean,
    cartFeedModelList: LazyPagingItems<CartFeedModel>,
    scrollState: LazyListState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        containerColor = MoonlightTheme.colors.background,
    ) { paddingValues ->
        when {
            cartFeedModelList.loadState.refresh is LoadState.Loading -> {
                LoadingFeed(
                    modifier = modifier
                        .padding(top = paddingValues.calculateTopPadding()),
                )
            }
            cartFeedModelList.loadState.refresh is LoadState.Error -> {
                ErrorFeed(
                    modifier = modifier
                        .padding(top = paddingValues.calculateTopPadding()),
                    onRepeatClick = cartFeedModelList::refresh
                )
            }
            isPagerEmpty(cartFeedModelList.itemCount) -> {
                EmptyFeed(
                    modifier = modifier
                        .padding(top = paddingValues.calculateTopPadding()),
                    onVisitCatalogClick = onVisitCatalogClick,
                )
            }
            else -> {
                FilledFeed(
                    modifier = modifier
                        .padding(top = paddingValues.calculateTopPadding()),
                    onProductClick = onProductClick,
                    onCheckChange = onCheckChange,
                    onDeleteClick = onDeleteClick,
                    onFavouriteClick = onFavouriteClick,
                    onIncreaseProductCountClick = onIncreaseProductCountClick,
                    onDecreaseProductCountClick = onDecreaseProductCountClick,
                    onOrderClick = onOrderClick,
                    onSharedCheckBoxClick = onSharedCheckBoxClick,
                    isCheckSharedCheckBox = isCheckSharedCheckBox,
                    cartFeedModelList = cartFeedModelList,
                    scrollState = scrollState,
                )
            }
        }
    }



}

@Composable
private fun LoadingFeed(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
        verticalArrangement = Arrangement.spacedBy(
            space = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
        )
    ) {
        SkeletonCheckBoxComponent(isTextVisible = true)

        GridLayout(
            items = listOf(1, 2, 3),
            cellSize = 1,
            horizontalItemSpacing = 0.dp
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
        ) {
            SecondTitleTextWidget(text = stringResource(R.string.empty_cart))
            TextFieldTextWidget(text = stringResource(R.string.take_a_look_at_catalog))
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = MoonlightTheme.dimens.paddingFromEdges),
            contentAlignment = Alignment.BottomCenter
        ) {
            ButtonWidget(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
                onClick = onVisitCatalogClick,
                text = stringResource(R.string.visit_catalog)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FilledFeed(
    onProductClick: (id: Long) -> Unit,
    onCheckChange: (Long, Boolean) -> Unit,
    onDeleteClick: (Long) -> Unit,
    onFavouriteClick: (id: Long, currentStatus: Boolean) -> Unit,
    onOrderClick: () -> Unit,
    onIncreaseProductCountClick:(id: Long, count: Int) -> Unit,
    onDecreaseProductCountClick:(id: Long, count: Int) -> Unit,
    cartFeedModelList: LazyPagingItems<CartFeedModel>,
    scrollState: LazyListState,
    onSharedCheckBoxClick: (Boolean) -> Unit,
    isCheckSharedCheckBox: Boolean,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    var heightForScroll by remember { mutableStateOf(0.dp) }

    Column(
        modifier = modifier
            .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
        verticalArrangement = Arrangement.spacedBy(
            space = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical
        )
    ) {
        LazyColumn(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(
                space = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
            ),
            state = scrollState,
        ) {
            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(
                            color = MoonlightTheme.colors.background.copy(alpha = 0.7f),
                        ),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    CheckBoxWithTextComponent(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical / 2),
                        onCheckedChange = onSharedCheckBoxClick,
                        checked = isCheckSharedCheckBox,
                        text = "${stringResource(R.string.count)}: ${cartFeedModelList.itemCount}"
                    )
                }
            }

            items(
                items = cartFeedModelList.itemSnapshotList.items,
                key = { item -> item.itemId }
            ) { cartModel ->
                CartFeedItemComponent(
                    modifier = Modifier
                        .absoluteOffset(y = -(MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical/2))
                        .animateItem(fadeInSpec = null, fadeOutSpec = null),
                    onProductClick = onProductClick,
                    onCheckedChange = onCheckChange,
                    onDeleteClick = onDeleteClick,
                    onFavouriteClick = onFavouriteClick,
                    onPlusClick = onIncreaseProductCountClick,
                    onMinusClick = onDecreaseProductCountClick,
                    cartFeedModel = cartModel,
                )
            }

            when (cartFeedModelList.loadState.append) {
                is LoadState.Error -> {
                    item {
                        ErrorFeed(
                            onRepeatClick = cartFeedModelList::retry
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

            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightForScroll)
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MoonlightTheme.colors.background.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(
                        topStart = MoonlightTheme.dimens.buttonRadius,
                        topEnd = MoonlightTheme.dimens.buttonRadius
                    ),
                )
                .padding(top = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical)
                .onGloballyPositioned { layoutCoordinates ->
                    with(density) {
                        heightForScroll = layoutCoordinates.size.height.toDp()
                    }
                }
                .padding(bottom = MoonlightTheme.dimens.paddingBetweenComponentsMediumVertical),
            verticalArrangement = Arrangement.spacedBy(
                space = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
            )
        ) {
            PrecheckBox(
                modifier = Modifier
                    .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
                countOfChosenProducts = calculateCountOfChosenProducts(cartFeedModelList.itemSnapshotList.items),
                totalPrice = calculateTotalPrice(cartFeedModelList.itemSnapshotList.items),
            )

            OrderBtn(
                onOrderClick = onOrderClick,
                enable = calculateCountOfChosenProducts(cartFeedModelList.itemSnapshotList.items) > 0
            )
        }
    }
}

@Composable
private fun PrecheckBox(
    totalPrice: Double,
    countOfChosenProducts: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = MoonlightTheme.colors.card2,
                shape = MoonlightTheme.shapes.buttonShape,
            )
            .padding(MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                SubTitleTextWidget(text = "Итого")
                SubTitleTextWidget(text = "${totalPrice.toString().dropLast(2)} ₽")
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(
                        color = MoonlightTheme.colors.border,
                        shape = MoonlightTheme.shapes.buttonShape,
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                ButtonTextWidget(text = "${countOfChosenProducts} ${getCorrectFormOfWordProductOnRussian(countOfChosenProducts)} на сумму")
                ButtonTextWidget(text = "${totalPrice.toString().dropLast(2)} ₽")
            }
        }
    }
}

private fun calculateTotalPrice(items: List<CartFeedModel>): Double = items.filter { it.chosen }.sumOf { it.price.toDouble() * it.count }

private fun calculateCountOfChosenProducts(items: List<CartFeedModel>): Int = items.filter { it.chosen }.sumOf { it.count }

private fun getCorrectFormOfWordProductOnRussian(countOfProducts: Int): String {
    val OneThing = "товар"
    val FewThings = "товара"
    val ZeroOrManyThings = "товаров"

    return when (countOfProducts) {
        0 -> ZeroOrManyThings
        1 -> OneThing
        in 2..4 -> FewThings
        else -> ZeroOrManyThings
    }
}

@Composable
private fun OrderBtn(
    onOrderClick: () -> Unit,
    enable: Boolean,
    modifier: Modifier = Modifier
) {
    ButtonWidget(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
        onClick = onOrderClick,
        text = stringResource(R.string.order),
        enable = enable,
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

