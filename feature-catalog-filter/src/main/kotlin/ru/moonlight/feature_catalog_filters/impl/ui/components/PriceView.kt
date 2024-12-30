package ru.moonlight.feature_catalog_filters.impl.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import ru.moonlight.feature_catalog_filters.R
import ru.moonlight.theme.MoonlightTheme
import ru.moonlight.ui.TextFieldComponent
import ru.moonlight.utils.keyboard.clearFocusOnKeyboardDismiss
import ru.moonlight.utils.keyboard.keyboardAsState

@Composable
internal fun PriceView(
    defaultMinPrice: String,
    minPrice: String,
    defaultMaxPrice: String,
    maxPrice: String,
    onMinPriceChange: (String) -> Unit,
    onMaxPriceChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isKeyboardOpen by keyboardAsState()

    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = stringResource(R.string.price),
            style = MoonlightTheme.typography.button,
            color = MoonlightTheme.colors.text
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical),
            horizontalArrangement = Arrangement.spacedBy(MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
        ) {
            TextFieldComponent(
                modifier = Modifier
                    .weight(1f)
                    .clearFocusOnKeyboardDismiss(),
                value = minPrice,
                onValueChange = { newPrice ->
                    onMinPriceChange(newPrice)
                },
                placeholder = stringResource(R.string.from),
                keyboardType = KeyboardType.Number,
                isError = checkMinPrice(isKeyboardOpen, maxPrice, defaultMinPrice, minPrice),
            )
            TextFieldComponent(
                modifier = Modifier
                    .weight(1f)
                    .clearFocusOnKeyboardDismiss(),
                value = maxPrice,
                onValueChange = { newPrice ->
                    onMaxPriceChange(newPrice)
                },
                placeholder = stringResource(R.string.to),
                keyboardType = KeyboardType.Number,
                isError = checkMaxPrice(isKeyboardOpen, minPrice, defaultMaxPrice, maxPrice),
            )
        }
    }
}

private fun checkMaxPrice(isKeyboardOpen: Boolean, minPrice: String, defaultMaxPrice: String, newMaxPrice: String): Boolean {
    if (isKeyboardOpen) return false

    val _minPrice = if (minPrice.isEmpty()) 0.0 else minPrice.toDouble()
    val _defaultMaxPrice = if (defaultMaxPrice.isEmpty()) 0.0 else defaultMaxPrice.toDouble()
    val _newMaxPrice = if (newMaxPrice.isEmpty()) 0.0 else newMaxPrice.toDouble()

    return (
        (_newMaxPrice < _minPrice) ||
        (_newMaxPrice > _defaultMaxPrice)
    )
}

private fun checkMinPrice(isKeyboardOpen: Boolean, maxPrice: String, defaultMinPrice: String, newMinPrice: String): Boolean {
    if (isKeyboardOpen) return false

    val _maxPrice = if (maxPrice.isEmpty()) 0.0 else maxPrice.toDouble()
    val _defaultMinPrice = if (defaultMinPrice.isEmpty()) 0.0 else defaultMinPrice.toDouble()
    val _newMinPrice = if (newMinPrice.isEmpty()) 0.0 else newMinPrice.toDouble()

    return (
        (_newMinPrice > _maxPrice) ||
        (_newMinPrice < _defaultMinPrice)
    )
}