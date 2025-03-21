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
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.utils.keyboard.keyboardAsState
import ru.moonlight.api.widget.textfield.TextFieldWidgetWithoutInsideState
import ru.moonlight.feature_catalog_filters.R

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
            MinPriceTextField(
                modifier = Modifier
                    .weight(1f),
                minPrice = minPrice,
                onPriceChange = onMinPriceChange,
                isKeyboardOpen = isKeyboardOpen,
                currentMaxPrice = maxPrice,
                defaultMinPrice = defaultMinPrice
            )
            MaxPriceTextField(
                modifier = Modifier
                    .weight(1f),
                maxPrice = maxPrice,
                onPriceChange = onMaxPriceChange,
                isKeyboardOpen = isKeyboardOpen,
                currentMinPrice = minPrice,
                defaultMaxPrice = defaultMaxPrice
            )
        }
    }
}

@Composable
private fun MinPriceTextField(
    minPrice: String,
    onPriceChange: (String) -> Unit,
    isKeyboardOpen: Boolean,
    currentMaxPrice: String,
    defaultMinPrice: String,
    modifier: Modifier = Modifier,
) {
    TextFieldWidgetWithoutInsideState(
        modifier = modifier,
        initialText = minPrice,
        onFocusLost = onPriceChange,
        placeholder = stringResource(R.string.from),
        keyboardType = KeyboardType.Number,
        isError = checkMinPrice(isKeyboardOpen, currentMaxPrice, defaultMinPrice, minPrice),
    )
}

@Composable
private fun MaxPriceTextField(
    maxPrice: String,
    onPriceChange: (String) -> Unit,
    isKeyboardOpen: Boolean,
    currentMinPrice: String,
    defaultMaxPrice: String,
    modifier: Modifier = Modifier,
) {
    TextFieldWidgetWithoutInsideState(
        modifier = modifier,
        initialText = maxPrice,
        onFocusLost = onPriceChange,
        placeholder = stringResource(R.string.to),
        keyboardType = KeyboardType.Number,
        isError = checkMaxPrice(isKeyboardOpen, currentMinPrice, defaultMaxPrice, maxPrice),
    )
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