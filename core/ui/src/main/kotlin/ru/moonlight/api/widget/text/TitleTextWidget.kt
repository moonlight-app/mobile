package ru.moonlight.api.widget.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import ru.moonlight.impl.template.TextTemplate
import ru.moonlight.api.theme.MoonlightTheme

@Composable
fun TitleTextWidget(
    text: String,
    textColor: Color = MoonlightTheme.colors.text,
    textStyle: TextStyle = MoonlightTheme.typography.title,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    minLines: Int = 1,
    textAlign: TextAlign? = null,
) {
    TextTemplate(
        text = text,
        textColor = textColor,
        textStyle = textStyle,
        overflow = overflow,
        modifier = modifier,
        maxLines = maxLines,
        minLines = minLines,
        textAlign = textAlign,
    )
}
