package ru.moonlight.feature_auth_signup_registration.registration.impl.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.widget.button.ButtonWidget
import ru.moonlight.feature_auth_signup_registration.R

@Composable
internal fun CreateAccountButton(
    onCreateClick: () -> Unit,
    enable: Boolean,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) {
    ButtonWidget(
        modifier = modifier
            .navigationBarsPadding()
            .fillMaxWidth(0.55f),
        onClick = onCreateClick,
        text = stringResource(R.string.createAccount),
        enable = enable,
        isLoading = isLoading,
    )
}

