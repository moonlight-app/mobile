package ru.moonlight.feature_auth_signup_complete.impl.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.widget.button.ButtonWidget
import ru.moonlight.feature_auth_signup_complete.R

@Composable
internal fun StartButton(
    onStartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ButtonWidget(
        modifier = modifier
            .fillMaxWidth(0.55f),
        onClick = onStartClick,
        text = stringResource(R.string.getStart),
    )
}