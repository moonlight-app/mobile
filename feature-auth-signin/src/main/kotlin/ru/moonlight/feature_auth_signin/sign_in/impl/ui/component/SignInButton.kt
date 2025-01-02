package ru.moonlight.feature_auth_signin.sign_in.impl.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.widget.button.ButtonWidget
import ru.moonlight.feature_auth_signin.R

@Composable
internal fun SignInButton(
    onSignInClick: () -> Unit,
    enable: Boolean,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    ButtonWidget(
        modifier = modifier
            .fillMaxWidth(0.55f),
        onClick = onSignInClick,
        text = stringResource(R.string.signInAccount),
        enable = enable,
        isLoading = isLoading,
    )
}