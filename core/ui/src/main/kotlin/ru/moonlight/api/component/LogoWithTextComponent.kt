package ru.moonlight.api.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.text.DescriptionTextWidget
import ru.moonlight.api.widget.text.SubTitleTextWidget
import ru.moonlight.impl.template.AnnotatedTextTemplate
import ru.moonlight.ui.R

@Composable
fun LogoWithTextComponent(
    modifier: Modifier = Modifier,
    onAnnotatedTextClick: () -> Unit = {},
    subTitleText: String? = null,
    bodyText: String? = null,
    bodyPart2Text : String? = null,
    bodyPart3Text : String? = null,
    subTitleTextColor: Color = MoonlightTheme.colors.text,
    bodyTextColor: Color = MoonlightTheme.colors.hintText,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
            alignment = Alignment.Top,
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(painter = painterResource(R.drawable.logo), contentDescription = "Moonlight logo")
        }
        if (!subTitleText.isNullOrEmpty()) {
            SubTitleTextWidget(
                text = subTitleText,
                textColor = subTitleTextColor,
            )
        }
        if (!bodyText.isNullOrEmpty()) {
            if (bodyPart2Text != null && bodyPart3Text != null) {
                AnnotatedTextTemplate(
                    onClick = onAnnotatedTextClick,
                    textPart1 = bodyText,
                    textPart2 = bodyPart2Text,
                    textPart3 = bodyPart3Text,
                    withLineBreaks = true,
                )
            } else {
                DescriptionTextWidget(
                    text = bodyText,
                    textColor = bodyTextColor,
                )
            }
        }
    }
}