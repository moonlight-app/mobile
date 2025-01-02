package ru.moonlight.feature_catalog_sort.impl.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.text.ButtonTextWidget
import ru.moonlight.feature_catalog_sort.R

@Composable
internal fun Title(modifier: Modifier = Modifier) {
    ButtonTextWidget(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical),
        text = stringResource(R.string.sort),
        textAlign = TextAlign.Center,
    )
}

