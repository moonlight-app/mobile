package ru.moonlight.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import ru.moonlight.theme.MoonlightTheme

@Composable
private fun ProgressBarTemplate(
    size: Dp,
    color: Color,
    strokeWidth: Dp,
    modifier: Modifier = Modifier,
) {
    CircularProgressIndicator(
        modifier = modifier
            .size(size),
        color = color,
        strokeWidth = strokeWidth
    )
}

@Composable
fun ProgressBarComponent(
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

@Composable
fun ProgressBarSmallComponent(
    modifier: Modifier = Modifier,
    color: Color = MoonlightTheme.colors.component,
    size: Dp = MoonlightTheme.dimens.smallProgressBarSize,
    strokeWidth: Dp = MoonlightTheme.dimens.smallProgressBarWidth,
) {
    ProgressBarTemplate(
        modifier = modifier,
        size = size,
        color = color,
        strokeWidth = strokeWidth
    )
}