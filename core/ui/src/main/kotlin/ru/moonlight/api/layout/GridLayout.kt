package ru.moonlight.api.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import ru.moonlight.api.theme.MoonlightTheme

@Composable
fun <T> GridLayout(
    items: List<T>,
    cellSize: Int,
    modifier: Modifier = Modifier,
    verticalItemSpacing: Dp = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
    horizontalItemSpacing: Dp = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal,
    item: @Composable (T) -> Unit,
) {
    require(cellSize > 0) { "CellSize must be greater than 0" }
    require(items.first() !is Iterable<*>) { "Illegal argument, T must be not Iterable" }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            space = verticalItemSpacing,
        )
    ) {
        val rows = items.chunked(cellSize)
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = horizontalItemSpacing,
                )
            ) {
                rowItems.forEach { itemInfo ->
                    Box(modifier = Modifier.weight(1f)) {
                        item(itemInfo)
                    }
                }
                repeat(cellSize - rowItems.size) {
                    Box(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun <K, V> GridLayout(
    items: Map<K, V>,
    cellSize: Int,
    modifier: Modifier = Modifier,
    item1: @Composable (K) -> Unit,
    item2: @Composable (V) -> Unit,
) {
    require(cellSize > 0) { "CellSize must be greater than 0" }
    require(items.keys.isNotEmpty()) { "Map must not be empty" }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            space = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical
        )
    ) {
        items.forEach { (key, value) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal
                )
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    item1(key)
                }
                Box(modifier = Modifier.weight(1f)) {
                    item2(value)
                }

            }
        }
    }
}