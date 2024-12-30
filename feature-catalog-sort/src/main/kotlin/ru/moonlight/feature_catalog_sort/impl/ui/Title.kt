package ru.moonlight.feature_catalog_sort.impl.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ru.moonlight.feature_catalog_sort.R
import ru.moonlight.theme.MoonlightTheme

@Composable
internal fun Title(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical),
        text = stringResource(R.string.sort),
        style = MoonlightTheme.typography.button,
        textAlign = TextAlign.Center,
    )
}

