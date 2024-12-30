package ru.moonlight.feature_catalog.impl.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.feature_catalog.R
import ru.moonlight.theme.MoonlightTheme

@Composable
internal fun ErrorText(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.somethingWentWrong),
            style = MoonlightTheme.typography.button
        )
    }
}