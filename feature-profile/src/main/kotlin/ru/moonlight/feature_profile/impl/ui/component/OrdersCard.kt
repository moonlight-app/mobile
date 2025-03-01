package ru.moonlight.feature_profile.impl.ui.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import ru.moonlight.api.animation.bouncingClickable
import ru.moonlight.api.component.OrderItem
import ru.moonlight.api.component.OrderUiModel
import ru.moonlight.api.component.SkeletonOrderItem
import ru.moonlight.api.component.convertStatusToUiModel
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.button.OutlinedButtonWidget
import ru.moonlight.api.widget.text.ButtonTextWidget
import ru.moonlight.api.widget.text.SubTitleTextWidget
import ru.moonlight.feature_profile.R
import kotlin.math.absoluteValue

@Composable
internal fun OrdersCard(
    onClick: () -> Unit,
    orders: LazyPagingItems<OrderUiModel>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = MoonlightTheme.shapes.buttonShape)
            .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal)
            .bouncingClickable { onClick() },
        shape = MoonlightTheme.shapes.buttonShape,
        colors = CardDefaults.cardColors(
            containerColor = MoonlightTheme.colors.card2,
            contentColor = MoonlightTheme.colors.text,
        )
    ) {
        Column (
            modifier = Modifier
                .padding(vertical = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(
                space = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
            )
        ) {
            SubTitleTextWidget(
                modifier = Modifier.padding(
                    start = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal
                ),
                text = stringResource(R.string.orders),
            )

            when {
                orders.loadState.refresh is LoadState.Loading -> {
                    LoadingPager()
                }
                orders.loadState.refresh is LoadState.Error -> {
                    ErrorPager(
                        onRepeatClick = orders::refresh
                    )
                }
                isPagerEmpty(orders.itemCount) -> {
                    EmptyOrderPager(
                        modifier = modifier,
                    )
                }
                else -> {
                    FilledOrderPager(list = orders)
                }
            }
        }
    }
}

private fun isPagerEmpty(count: Int) = (count == 0)

@Composable
fun LoadingPager(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(
        pageCount = { 2 }
    )

    val flingBehavior = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(1),
        snapAnimationSpec = tween(
            easing = FastOutSlowInEasing,
            durationMillis = 400
        ),
    )

    HorizontalPager(
        modifier = modifier
            .fillMaxWidth(),
        state = pagerState,
        pageSpacing = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal,
        flingBehavior = flingBehavior,
        pageSize = object : PageSize {
            override fun Density.calculateMainAxisPageSize(
                availableSpace: Int,
                pageSpacing: Int
            ): Int {
                return ((availableSpace - 2 * pageSpacing) * 0.90f).toInt()
            }
        },
        contentPadding = PaddingValues(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
        snapPosition = SnapPosition.Center,
        key = { page -> page },
    ) { page ->
        SkeletonOrderItem(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    val pageOffSet = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffSet.coerceIn(0f, 1f)
                    )
                    scaleY = lerp(
                        start = 0.9f,
                        stop = 1f,
                        fraction = 1f - pageOffSet.coerceIn(0f, 1f)
                    )
                },
        )

    }
}

@Composable
private fun ErrorPager(
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
            text = stringResource(ru.moonlight.ui.R.string.somethingWentWrong),
        )
        OutlinedButtonWidget(
            onClick = onRepeatClick,
            text = stringResource(ru.moonlight.ui.R.string.repeat),
        )
    }
}

@Composable
private fun EmptyOrderPager(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical),
    ) {
        ButtonTextWidget(
            text = "У вас пока что нет заказов"
        )
    }
}

@Composable
private fun FilledOrderPager(
    list: LazyPagingItems<OrderUiModel>,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val pagerState = rememberPagerState(
        pageCount = { list.itemCount }
    )

    val flingBehavior = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(1),
        snapAnimationSpec = tween(
            easing = FastOutSlowInEasing,
            durationMillis = 400
        ),
    )

    if (list.itemCount == 1) {
        val order = list[0]
        OrderItem(
            modifier = modifier
                .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
            imageUrl = order?.imageUrl ?: "",
            title = order?.title ?: "Unknown name",
            status = order?.status?.convertStatusToUiModel(context = context) ?: "Unknown status",
        )
    } else {
        HorizontalPager(
            modifier = modifier
                .fillMaxWidth(),
            state = pagerState,
            pageSpacing = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal,
            flingBehavior = flingBehavior,
            pageSize = object : PageSize {
                override fun Density.calculateMainAxisPageSize(
                    availableSpace: Int,
                    pageSpacing: Int
                ): Int {
                    return ((availableSpace - 2 * pageSpacing) * 0.90f).toInt()
                }
            },
            snapPosition = SnapPosition.Center,
            key = { page -> page },
        ) { page ->
            val order = list[page]
            OrderItem(
                modifier = Modifier
                    //delete when Horizontal Pager will be fixed with contentPadding and SnapPosition.Center
                    .padding(
                        start = if (page == 0) MoonlightTheme.dimens.paddingBetweenComponentsHorizontal else 0.dp,
                        end = if (page == list.itemCount - 1) MoonlightTheme.dimens.paddingBetweenComponentsHorizontal else 0.dp
                    )
                    .graphicsLayer {
                        val pageOffSet = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffSet.coerceIn(0f, 1f)
                        )
                        scaleY = lerp(
                            start = 0.9f,
                            stop = 1f,
                            fraction = 1f - pageOffSet.coerceIn(0f, 1f)
                        )
                    },
                imageUrl = order?.imageUrl ?: "",
                title = order?.title ?: "Unknown name",
                status = order?.status?.convertStatusToUiModel(context = context) ?: "Unknown status",
            )
        }
    }
}

