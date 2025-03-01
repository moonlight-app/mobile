package ru.moonlight.api.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.CheckBoxWidget
import ru.moonlight.api.widget.text.SmallButtonTextWidget

@Composable
fun CheckBoxWithTextComponent(
    onCheckedChange: (Boolean) -> Unit,
    checked: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    checkedBackgroundColor: Color = MoonlightTheme.colors.outlineHighlightComponent,
    checkedColor: Color = Color.Transparent,
    checkmarkColor: Color = MoonlightTheme.colors.text,
    uncheckedBackgroundColor: Color = MoonlightTheme.colors.card,
    uncheckedColor: Color = MoonlightTheme.colors.border,
    textColor: Color = MoonlightTheme.colors.text,
    textStyle: TextStyle = MoonlightTheme.typography.smallButton
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal
        )
    ) {
        CheckBoxWidget(
            onCheckedChange = onCheckedChange,
            checked = checked,
            checkedBackgroundColor = checkedBackgroundColor,
            checkedColor = checkedColor,
            checkmarkColor = checkmarkColor,
            uncheckedBackgroundColor = uncheckedBackgroundColor,
            uncheckedColor = uncheckedColor,
        )
        SmallButtonTextWidget(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            text = text,
            textColor = textColor,
            textStyle = textStyle,
        )
    }
}