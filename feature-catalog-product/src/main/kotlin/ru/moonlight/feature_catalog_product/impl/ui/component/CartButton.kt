package ru.moonlight.feature_catalog_product.impl.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.widget.button.ButtonWidget
import ru.moonlight.feature_catalog_product.R

@Composable
fun CartButton(
    favouritesBtnVisible: Boolean,
    enable: Boolean,
    onCartClick: () -> Unit,
    goToCartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ButtonWidget(
        modifier = modifier,
        onClick = {
            if (favouritesBtnVisible) onCartClick()
            else goToCartClick()
        },
        enable = enable,
        text = if (favouritesBtnVisible)
            stringResource(R.string.to_cart)
        else
            stringResource(R.string.go_to_cart ),
    )
}