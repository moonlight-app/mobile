package ru.moonlight.feature_catalog_product.impl.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.moonlight.api.widget.text.SecondTitleTextWidget

@Composable
internal fun TitleText(
    text: String,
    modifier: Modifier = Modifier,
) {
    SecondTitleTextWidget(
        modifier = modifier,
        text = text,
        maxLines = 2,
    )
}