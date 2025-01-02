package ru.moonlight.api.widget.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.impl.template.ButtonTemplate

@Composable
fun OutlinedButtonWidget(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    isLoading: Boolean = false,
    containerColor: Color = Color.Transparent,
    contentColor: Color = MoonlightTheme.colors.highlightText,
    disabledColor: Color = Color.Transparent,
    disabledTextColor: Color = MoonlightTheme.colors.disabledText,
    borderColor: Color = MoonlightTheme.colors.outlineHighlightComponent,
    disabledBorderColor: Color = MoonlightTheme.colors.disabledComponent,
    progressBarColor: Color = MoonlightTheme.colors.text,
) {
    ButtonTemplate(
        onClick = onClick,
        text = text,
        modifier = modifier,
        enable = enable,
        isLoading = isLoading,
        containerColor = containerColor,
        contentColor = contentColor,
        disabledColor = disabledColor,
        disabledBorderColor = disabledBorderColor,
        disabledTextColor = disabledTextColor,
        borderColor = borderColor,
        progressBarColor = progressBarColor,
    )
}