package ru.moonlight.feature_catalog_filters.impl.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.layout.GridLayout

@Composable
internal fun <T> FilterCard(
    title: String,
    cellsSize: Int,
    list: List<T>,
    modifier: Modifier = Modifier,
    item: @Composable (T) -> Unit,
) {
    if (list.isNotEmpty()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MoonlightTheme.typography.button,
                color = MoonlightTheme.colors.text,
            )
            GridLayout(
                modifier = Modifier
                    .padding(top = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical),
                items = list,
                cellSize = cellsSize,
            ) {
                item(it)
            }
        }
    }
}