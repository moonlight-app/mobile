package ru.moonlight.feature_catalog_product.impl.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.component.CardWithTitleAndGrid
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.button.ButtonChipWidget
import ru.moonlight.feature_catalog_product.R

@Composable
internal fun SizesCard(
    onSizeClick: (String) -> Unit,
    sizes: List<String>,
    chosenSize: String,
    modifier: Modifier = Modifier,
) {
    CardWithTitleAndGrid(
        modifier = modifier
            .background(
                color = MoonlightTheme.colors.card2,
                shape = MoonlightTheme.shapes.buttonShape,
            )
            .padding(MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical),
        cellsSize = 4,
        list = sizes,
        title = stringResource(R.string.size),
        item = { size ->
            ButtonChipWidget(
                onClick = { onSizeClick(size) },
                selected = size == chosenSize,
                text = size,
            )
        }
    )
}