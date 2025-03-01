package ru.moonlight.feature_catalog_product.impl.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.component.CardWithTitleAndGrid
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.text.DescriptionTextWidget
import ru.moonlight.feature_catalog_product.R

@Composable
internal fun DescriptionCard(
    description: String,
    modifier: Modifier = Modifier,
) {
    CardWithTitleAndGrid(
        modifier = modifier
            .background(
                color = MoonlightTheme.colors.card2,
                shape = MoonlightTheme.shapes.buttonShape,
            )
            .padding(MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical),
        cellsSize = 1,
        list = listOf(description.replace("\\n", "\n")),
        title = stringResource(R.string.description),
        item = {
            DescriptionTextWidget(text = it)
        },
    )
}