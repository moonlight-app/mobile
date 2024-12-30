package ru.moonlight.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.moonlight.theme.MoonlightTheme

@Composable
private fun ButtonTemplate(
    onClick: () -> Unit,
    text: String,
    containerColor: Color,
    contentColor: Color,
    disabledColor: Color,
    disabledTextColor: Color,
    borderColor: Color,
    disabledBorderColor: Color,
    progressBarColor: Color,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    isLoading: Boolean = false,
    textStyle: TextStyle = MoonlightTheme.typography.button
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = (enable && !isLoading),
        colors = ButtonDefaults.buttonColors (
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledColor,
            disabledContentColor = disabledTextColor,
        ),
        shape = MoonlightTheme.shapes.buttonShape,
        border = BorderStroke(
            MoonlightTheme.dimens.buttonBorderWidth,
            color = if (enable) borderColor else disabledBorderColor
        ),
        contentPadding = PaddingValues(
            horizontal = 32.dp,
            vertical = 16.dp,
        )
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .alpha(if (isLoading) 0f else 1f),
                text = text,
                style = textStyle,
                textAlign = TextAlign.Center,
            )
            if (isLoading) ProgressBarSmallComponent(color = progressBarColor)
        }
    }
}

@Composable
fun ButtonComponent(
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

@Composable
fun ButtonOutlinedComponent(
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

@Composable
fun ButtonChipComponent(
    onClick: () -> Unit,
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Transparent,
    selectedContainerColor: Color = MoonlightTheme.colors.highlightComponent,
    labelColor: Color = MoonlightTheme.colors.outlineHighlightComponent,
    selectedLabelColor: Color = MoonlightTheme.colors.text,
    labelTextStyle: TextStyle = MoonlightTheme.typography.smallButton,
    animationDuration: Int = 200
) {
    val animatedContainerColor by animateColorAsState(
        targetValue = if (selected) selectedContainerColor else containerColor,
        animationSpec = tween(durationMillis = animationDuration),
        label = ""
    )

    val animatedBorderColor by animateColorAsState(
        targetValue = if (selected) containerColor else labelColor,
        animationSpec = tween(durationMillis = animationDuration),
        label = ""
    )

    val animatedTextColor by animateColorAsState(
        targetValue = if (selected) selectedLabelColor else labelColor,
        animationSpec = tween(durationMillis = animationDuration),
        label = ""
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = animatedContainerColor,
                shape = MoonlightTheme.shapes.buttonShape
            )
            .border(
                width = 1.dp,
                color = animatedBorderColor,
                shape = MoonlightTheme.shapes.buttonShape
            )
            .clip(shape = MoonlightTheme.shapes.buttonShape)
            .clickable(onClick = onClick)
            .padding(
                horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal,
                vertical = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
            )
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical / 2),
            text = text,
            style = labelTextStyle.copy(color = animatedTextColor),
        )
    }
}