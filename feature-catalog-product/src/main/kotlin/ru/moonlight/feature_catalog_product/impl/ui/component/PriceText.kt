package ru.moonlight.feature_catalog_product.impl.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.moonlight.api.widget.text.TitleTextWidget

@Composable
internal fun PriceText(
    text: String,
    modifier: Modifier = Modifier,
) {
    TitleTextWidget(
        modifier = modifier,
        text = text.dropLast(2) + " ₽",
    )
}