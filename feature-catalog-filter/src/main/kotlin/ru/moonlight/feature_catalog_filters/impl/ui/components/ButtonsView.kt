package ru.moonlight.feature_catalog_filters.impl.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.feature_catalog_filters.R
import ru.moonlight.theme.MoonlightTheme
import ru.moonlight.ui.ButtonComponent
import ru.moonlight.ui.ButtonOutlinedComponent

@Composable
internal fun ButtonsView(
    onClearClick: () -> Unit,
    onApplyClick: () -> Unit,
    applyEnable: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(color = MoonlightTheme.colors.background.copy(alpha = 0.7f))
            .padding(
                bottom = MoonlightTheme.dimens.paddingBetweenComponentsBigVertical,
                top = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
            horizontalArrangement = Arrangement.spacedBy(MoonlightTheme.dimens.paddingBetweenComponentsHorizontal)
        ) {
            ButtonOutlinedComponent(
                modifier = Modifier
                    .weight(1f),
                onClick = onClearClick,
                text = stringResource(R.string.clear),
            )
            ButtonComponent(
                modifier = Modifier
                    .weight(1f),
                onClick = onApplyClick,
                text = stringResource(R.string.apply),
                enable = applyEnable,
            )
        }
    }
}