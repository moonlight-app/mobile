package ru.moonlight.impl.template

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.progressbar.SmallProgressBarWidget

@Composable
internal fun ButtonTemplate(
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
            if (isLoading) SmallProgressBarWidget(color = progressBarColor)
        }
    }
}