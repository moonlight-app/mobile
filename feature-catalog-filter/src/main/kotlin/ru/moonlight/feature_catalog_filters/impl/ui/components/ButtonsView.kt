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
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.button.ButtonWidget
import ru.moonlight.api.widget.button.OutlinedButtonWidget
import ru.moonlight.feature_catalog_filters.R

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
            OutlinedButtonWidget(
                modifier = Modifier
                    .weight(1f),
                onClick = onClearClick,
                text = stringResource(R.string.clear),
            )
            ButtonWidget(
                modifier = Modifier
                    .weight(1f),
                onClick = onApplyClick,
                text = stringResource(R.string.apply),
                enable = applyEnable,
            )
        }
    }
}