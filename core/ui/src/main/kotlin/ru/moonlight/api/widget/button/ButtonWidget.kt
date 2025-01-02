package ru.moonlight.api.widget.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.impl.template.ButtonTemplate

@Composable
fun ButtonWidget(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    isLoading: Boolean = false,
    containerColor: Color = MoonlightTheme.colors.highlightComponent,
    contentColor: Color = MoonlightTheme.colors.highlightText,
    borderColor: Color = Color.Transparent,
    disabledBorderColor: Color = Color.Transparent,
    disabledColor: Color = MoonlightTheme.colors.disabledComponent,
    disabledTextColor: Color = MoonlightTheme.colors.disabledText,
    progressBarColor: Color = MoonlightTheme.colors.text,
) {
    ButtonTemplate(
        modifier = modifier,
        onClick = onClick,
        enable = enable,
        isLoading = isLoading,
        text = text,
        containerColor = containerColor,
        contentColor = contentColor,
        borderColor = borderColor,
        disabledBorderColor = disabledBorderColor,
        disabledColor = disabledColor,
        disabledTextColor = disabledTextColor,
        progressBarColor = progressBarColor,
    )
}