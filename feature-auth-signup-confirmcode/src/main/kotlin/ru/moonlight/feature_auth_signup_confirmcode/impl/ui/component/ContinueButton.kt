package ru.moonlight.feature_auth_signup_confirmcode.impl.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.widget.button.ButtonWidget
import ru.moonlight.feature_auth_signup_confirmcode.R

@Composable
internal fun ContinueButton(
    onContinueClick: () -> Unit,
    enable: Boolean,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) {
    ButtonWidget(
        modifier = modifier
            .fillMaxWidth(0.55f),
        enable = enable,
        isLoading = isLoading,
        onClick = onContinueClick,
        text = stringResource(R.string.continuee),
    )
}