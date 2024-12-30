package ru.moonlight.feature_catalog_filters.impl.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.feature_catalog_filters.R
import ru.moonlight.ui.ButtonChipComponent

@Composable
internal fun ProductSizeView(
    onSizeClick: (Double) -> Unit,
    sizes: List<Double>,
    chosenSizes: List<Double>,
    modifier: Modifier = Modifier,
) {
    FilterCard(
        modifier = modifier,
        cellsSize = 4,
        list = sizes,
        title = stringResource(R.string.size),
    ) { size ->
        ButtonChipComponent(
            onClick = { onSizeClick(size) },
            selected = chosenSizes.contains(size),
            text = size.toString(),
        )
    }
}