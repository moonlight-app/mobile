package ru.moonlight.api.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.moonlight.api.theme.MoonlightTheme

@Composable
fun CheckBoxWidget(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit),
    checkBoxSize: Dp = 24.dp,
    checkBoxBorderWidth: Dp = 1.dp,
    checkedColor: Color,
    checkedBackgroundColor: Color,
    checkmarkColor: Color,
    uncheckedBackgroundColor: Color,
    uncheckedColor: Color,
    animationDuration: Int = 200
) {
    // Animate the background color based on the checked state
    val backgroundColor by animateColorAsState(
        targetValue = if (checked) checkedBackgroundColor else uncheckedBackgroundColor,
        animationSpec = tween(durationMillis = animationDuration),
        label = ""
    )

    val _checkedColor by animateColorAsState(
        targetValue = if (checked) checkedColor else uncheckedColor,
        animationSpec = tween(durationMillis = animationDuration),
        label = ""
    )

    Box(
        modifier = Modifier
            .clip(shape = MoonlightTheme.shapes.checkBoxShape)
            .size(checkBoxSize)
            .background(backgroundColor)
            .clickable { onCheckedChange(!checked) }
            .border(
                BorderStroke(
                    checkBoxBorderWidth,
                    _checkedColor
                ),
                shape = MoonlightTheme.shapes.checkBoxShape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedVisibility(
            visible = checked,
            enter = scaleIn(initialScale = 0.8f) + fadeIn(tween(animationDuration)),
            exit = scaleOut(targetScale = 0.8f) + fadeOut(tween(animationDuration)),
        ) {
            Icon(
                Icons.Default.Check,
                tint = checkmarkColor,
                contentDescription = "Custom CheckBox"
            )
        }
    }
}

