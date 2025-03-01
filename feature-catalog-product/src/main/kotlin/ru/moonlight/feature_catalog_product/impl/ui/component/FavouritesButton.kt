package ru.moonlight.feature_catalog_product.impl.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.button.OutlinedButtonWidget
import ru.moonlight.feature_catalog_product.R

@Composable
fun FavouritesButton(
    onFavouritesClick:() -> Unit,
    isFavourite: Boolean,
    modifier: Modifier = Modifier,
) {
    OutlinedButtonWidget(
        modifier = modifier,
        onClick = onFavouritesClick,
        text = if (isFavourite) "Из избранного" else stringResource(R.string.to_favourites),
        contentColor = if (isFavourite) MoonlightTheme.colors.highlightComponent
                         else MoonlightTheme.colors.outlineHighlightComponent,
        borderColor = if (isFavourite) MoonlightTheme.colors.highlightComponent
                      else MoonlightTheme.colors.outlineHighlightComponent,
    )
}