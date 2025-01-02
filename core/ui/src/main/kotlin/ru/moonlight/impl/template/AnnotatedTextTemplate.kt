package ru.moonlight.impl.template

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import ru.moonlight.api.theme.MoonlightTheme

@Composable
internal fun AnnotatedTextTemplate(
    onClick: () -> Unit,
    textPart1: String,
    textPart2: String,
    textPart3: String,
    modifier: Modifier = Modifier,
    withLineBreaks: Boolean = false,
    textDescriptionColor: Color = MoonlightTheme.colors.hintText,
    textHighlightColor: Color = MoonlightTheme.colors.text,
    textStyle: TextStyle = MoonlightTheme.typography.description,
) {
    val annotatedText = buildAnnotatedString {
        withStyle(style = textStyle.copy(color = textDescriptionColor).toSpanStyle()) {
            append("$textPart1 " + if (withLineBreaks) "\n" else " ")
        }
        withStyle(style = SpanStyle(color = textHighlightColor)) {
            append("$textPart2 " + if (withLineBreaks) "\n" else " ")
        }
        withStyle(style = textStyle.copy(color = textDescriptionColor).toSpanStyle()) {
            append(textPart3)
        }
    }

    Text(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            },
        text = annotatedText,
        textAlign = TextAlign.Center,
    )
}