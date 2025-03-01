package ru.moonlight.api.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.button.ButtonWidget
import ru.moonlight.api.widget.text.SubTitleTextWidget
import ru.moonlight.api.widget.text.TextFieldTextWidget
import ru.moonlight.ui.R

@Composable
fun ErrorScreen(
    onRepeatAttemptClick: () -> Unit,
    errorMsg: String,
    buttonVisible: Boolean = true,
    buttonText: String = stringResource(R.string.repeatTry),
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.Center,
    ) {
        ErrorMessageComponent(
            errorText = errorMsg,
        )
    }

    if (buttonVisible) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(bottom = MoonlightTheme.dimens.paddingFromEdges),
            contentAlignment = Alignment.BottomCenter,
        ) {
            RepeatAttemptButton(
                onRepeatAttemptClick = onRepeatAttemptClick,
                buttonText = buttonText,
            )
        }
    }
}

@Composable
private fun ErrorMessageComponent(
    modifier: Modifier = Modifier,
    errorText: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(
            space = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
            alignment = Alignment.CenterVertically,
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SubTitleTextWidget(text = stringResource(R.string.appearError))
        TextFieldTextWidget(
            modifier = Modifier.padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
            text = errorText,
            textAlign = TextAlign.Center,
        )
        ErrorIcon()
    }
}

@Composable
private fun ErrorIcon(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(60.dp)
            .background(
                color = MoonlightTheme.colors.highlightComponent,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.error_icon),
            contentDescription = "Error",
            tint = MoonlightTheme.colors.highlightText
        )
    }
}

@Composable
private fun RepeatAttemptButton(
    onRepeatAttemptClick: () -> Unit,
    buttonText: String,
    modifier: Modifier = Modifier,
) {
    ButtonWidget(
        modifier = modifier,
        onClick = onRepeatAttemptClick,
        text = buttonText,
    )
}