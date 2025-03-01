package ru.moonlight.feature_catalog_filters.impl.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.component.CardWithTitleAndGrid
import ru.moonlight.api.widget.button.ButtonChipWidget
import ru.moonlight.feature_catalog_filters.R

@Composable
internal fun ProductSizeView(
    onSizeClick: (Float) -> Unit,
    sizes: List<Float>,
    chosenSizes: List<Float>,
    modifier: Modifier = Modifier,
) {
    CardWithTitleAndGrid(
        modifier = modifier,
        cellsSize = 4,
        list = sizes,
        title = stringResource(R.string.size),
    ) { size ->
        ButtonChipWidget(
            onClick = { onSizeClick(size) },
            selected = chosenSizes.contains(size),
            text = size.toString(),
        )
    }
}