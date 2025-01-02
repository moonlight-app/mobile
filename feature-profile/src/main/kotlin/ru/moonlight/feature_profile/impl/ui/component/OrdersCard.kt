package ru.moonlight.feature_profile.impl.ui.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import ru.moonlight.api.animation.bouncingClickable
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.text.DescriptionTextWidget
import ru.moonlight.api.widget.text.SubTitleTextWidget
import ru.moonlight.feature_profile.R
import ru.moonlight.feature_profile.impl.presentation.Orders
import kotlin.math.absoluteValue

@Composable
internal fun OrdersCard(
    onClick: () -> Unit,
    list: List<Orders>,
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
            OrderPager(list = list)
        }
    }
}

@Composable
private fun OrderPager(
    list: List<Orders>,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(
        pageCount = { list.size }
    )

    val flingBehavior = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(1),
        snapAnimationSpec = tween(
            easing = FastOutSlowInEasing,
            durationMillis = 400
        ),
    )

    if (list.isNotEmpty()) {
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
                        end = if (page == list.size - 1) MoonlightTheme.dimens.paddingBetweenComponentsHorizontal else 0.dp
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
                title = order.title,
                status = order.status,
            )
        }
    }
}

@Composable
private fun OrderItem(
    title: String,
    status: String,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MoonlightTheme.shapes.buttonShape,
        border = BorderStroke(
            1.dp,
            //TODO заменить, когда в модели Orders будут понятны статусы
            color = if (status != "бла") MoonlightTheme.colors.highlightComponent
            else MoonlightTheme.colors.disabledComponent
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = MoonlightTheme.colors.text,
        )
    ) {
        Row(
            modifier = Modifier
                .padding(MoonlightTheme.dimens.paddingBetweenComponentsHorizontal)
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(
                space = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal,
                alignment = Alignment.Start,
            ),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp, 36.dp)
                    .clip(MoonlightTheme.shapes.textFieldShape)
                    .background(color = MoonlightTheme.colors.highlightComponent)
            )
            Column(
                modifier = Modifier
                    .height(36.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                SubTitleTextWidget(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = title,
                    maxLines = 1,
                )
                DescriptionTextWidget(
                    text = status,
                    textColor = MoonlightTheme.colors.highlightComponent,
                )
            }
        }
    }
}