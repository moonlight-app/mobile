package ru.moonlight.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import ru.moonlight.theme.MoonlightTheme

@Composable
fun CheckBoxComponent(
    onCheckedChange: (Boolean) -> Unit,
    checked: Boolean,
    modifier: Modifier = Modifier,
    checkedColor: Color = MoonlightTheme.colors.highlightComponent,
    uncheckedColor: Color = MoonlightTheme.colors.hintText,
    checkmarkColor: Color = MoonlightTheme.colors.text,
) {
    Checkbox(
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        checked = checked,
        colors = CheckboxDefaults.colors(
            checkedColor = checkedColor,
            uncheckedColor = uncheckedColor,
            checkmarkColor = checkmarkColor
        )
    )
}

@Composable
fun CheckBoxWithTextComponent(
    onCheckedChange: (Boolean) -> Unit,
    checked: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    checkedColor: Color = MoonlightTheme.colors.highlightComponent,
    uncheckedColor: Color = MoonlightTheme.colors.hintText,
    checkmarkColor: Color = MoonlightTheme.colors.text,
    textStyle: TextStyle = MoonlightTheme.typography.smallButton,
    textColor: Color = MoonlightTheme.colors.text,
) {
    Row(
        modifier = modifier
            .wrapContentSize()
    ) {
        CheckBoxComponent(
            onCheckedChange = onCheckedChange,
            checked = checked,
            checkedColor = checkedColor,
            uncheckedColor = uncheckedColor,
            checkmarkColor = checkmarkColor
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            text = text,
            style = textStyle,
            color = textColor,
        )
    }
}