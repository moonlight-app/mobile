package ru.moonlight.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.moonlight.theme.MoonlightTheme

//@Composable
//private fun ButtonTemplate(
//    onClick: () -> Unit,
//    text: String,
//    containerColor: Color,
//    contentColor: Color,
//    disabledColor: Color,
//    disabledTextColor: Color,
//    borderColor: Color,
//    disabledBorderColor: Color,
//    progressBarColor: Color,
//    modifier: Modifier = Modifier,
//    enable: Boolean = true,
//    isLoading: Boolean = false,
//    textStyle: TextStyle = MoonlightTheme.typography.button
//) {
//    Button(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(MoonlightTheme.dimens.buttonHeight),
//        onClick = { onClick() },
//        enabled = (enable && !isLoading),
//        colors = ButtonDefaults.buttonColors (
//            containerColor = containerColor,
//            contentColor = contentColor,
//            disabledContainerColor = disabledColor,
//            disabledContentColor = disabledTextColor,
//        ),
//        shape = MoonlightTheme.shapes.buttonShape,
//
//        border = BorderStroke(
//            MoonlightTheme.dimens.buttonBorderWidth,
//            color = if (enable) borderColor else disabledBorderColor
//        ),
//    ) {
//        if (isLoading) {
//            ProgressBarSmallComponent(color = progressBarColor)
//        }
//        else {
//            Text(
//                text = text,
//                style = textStyle,
//                textAlign = TextAlign.Center,
//            )
//        }
//    }
//}

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
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        onClick = { onClick() },
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
    selected: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    containerColor: Color = MoonlightTheme.colors.card,
    selectedContainerColor: Color = MoonlightTheme.colors.highlightComponent,
    labelColor: Color = MoonlightTheme.colors.text,
    selectedLabelColor: Color = MoonlightTheme.colors.text,
    labelTextStyle: TextStyle = MoonlightTheme.typography.smallButton
) {
    FilterChip(
        modifier = modifier,
        onClick = { onClick() },
        selected = selected,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = containerColor,
            selectedContainerColor = selectedContainerColor,
            selectedLabelColor = selectedLabelColor,
            labelColor = labelColor,
        ),
        label = {
            Text(
                text = text,
                style = labelTextStyle,
            )
        },
    )
}