package ru.moonlight.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import ru.moonlight.theme.MoonlightTheme

@Composable
fun TextAnnotatedComponent(
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

@Composable
fun TextAuthComponent(
    modifier: Modifier = Modifier,
    onAnnotatedTextClick: () -> Unit = {},
    titleText: String = stringResource(id = R.string.app_name),
    subTitleText: String? = null,
    bodyText: String? = null,
    bodyPart2Text : String? = null,
    bodyPart3Text : String? = null,
    titleTextColor: Color = MoonlightTheme.colors.highlightComponent,
    subTitleTextColor: Color = MoonlightTheme.colors.text,
    bodyTextColor: Color = MoonlightTheme.colors.hintText,
    titleTextStyle: TextStyle = MoonlightTheme.typography.title,
    subTitleTextStyle: TextStyle = MoonlightTheme.typography.subTitle,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            modifier = Modifier,
            text = titleText,
            style = titleTextStyle,
            color = titleTextColor,
        )
        Spacer(modifier = Modifier.height(2.dp))
        if (!subTitleText.isNullOrEmpty()) {
            Text(
                modifier = Modifier,
                text = subTitleText,
                style = subTitleTextStyle,
                color = subTitleTextColor,
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        if (!bodyText.isNullOrEmpty()) {
            if (bodyPart2Text != null && bodyPart3Text != null) {
                TextAnnotatedComponent(
                    onClick = onAnnotatedTextClick,
                    textPart1 = bodyText,
                    textPart2 = bodyPart2Text,
                    textPart3 = bodyPart3Text,
                    withLineBreaks = true,
                )
            } else {
                Text(
                    modifier = Modifier,
                    text = bodyText,
                    style = MoonlightTheme.typography.description,
                    color = bodyTextColor,
                )
            }
        }
    }
}