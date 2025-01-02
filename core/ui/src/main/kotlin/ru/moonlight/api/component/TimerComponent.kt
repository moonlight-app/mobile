package ru.moonlight.api.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.impl.template.AnnotatedTextTemplate
import ru.moonlight.ui.R

@Composable
fun TimerComponent(
    onTryAgainClick: () -> Unit,
    timerText: String,
    dontGetCodeText: String = stringResource(R.string.dontGetCode),
    tryAgainText: String = stringResource(R.string.tryAgain),
    modifier: Modifier = Modifier
) {
    AnnotatedTextTemplate(
        onClick = onTryAgainClick,
        textPart1 = dontGetCodeText,
        textPart2 = tryAgainText,
        textPart3 = timerText,
        modifier = modifier,
    )
}

