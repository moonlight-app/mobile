package ru.moonlight.api.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.moonlight.api.layout.GridLayout
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.text.SubTitleTextWidget

@Composable
fun <T> CardWithTitleAndGrid(
    title: String,
    cellsSize: Int,
    list: List<T>,
    modifier: Modifier = Modifier,
    footer: @Composable () -> Unit = {},
    item: @Composable (T) -> Unit,
) {
    if (list.isNotEmpty()) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(
                MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical
            ),
        ) {
            SubTitleTextWidget(text = title)
            GridLayout(
                modifier = Modifier,
                items = list,
                cellSize = cellsSize,
            ) {
                item(it)
            }
            footer()
        }
    }
}

@Composable
fun <K, V> CardWithTitleAndGrid(
    title: String,
    cellsSize: Int,
    map: Map<K, V>,
    modifier: Modifier = Modifier,
    footer: @Composable () -> Unit = {},
    item1: @Composable (K) -> Unit,
    item2: @Composable (V) -> Unit,
) {
    if (map.isNotEmpty()) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(
                MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical
            ),
        ) {
            SubTitleTextWidget(text = title)
            GridLayout(
                modifier = Modifier,
                items = map,
                cellSize = cellsSize,
                item1 = item1,
                item2 = item2,
            )
            footer()
        }

    }
}