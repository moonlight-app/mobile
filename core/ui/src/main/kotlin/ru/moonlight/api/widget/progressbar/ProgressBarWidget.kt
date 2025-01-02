package ru.moonlight.api.widget.progressbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.impl.template.ProgressBarTemplate

@Composable
fun ProgressBarWidget(
    modifier: Modifier = Modifier,
    color: Color = MoonlightTheme.colors.component,
    size: Dp = MoonlightTheme.dimens.progressBarSize,
    strokeWidth: Dp = MoonlightTheme.dimens.progressBarWidth,
) {
    ProgressBarTemplate(
        modifier = modifier,
        size = size,
        color = color,
        strokeWidth = strokeWidth
    )
}