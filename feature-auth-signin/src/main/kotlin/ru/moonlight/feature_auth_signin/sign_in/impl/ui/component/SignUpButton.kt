package ru.moonlight.feature_auth_signin.sign_in.impl.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.widget.button.OutlinedButtonWidget
import ru.moonlight.feature_auth_signin.R

@Composable
internal fun SignUpButton(
    onSignUpClick: () -> Unit,
    enable: Boolean,
    modifier: Modifier = Modifier
) {
    OutlinedButtonWidget(
        modifier = modifier
            .fillMaxWidth(0.55f),
        onClick = onSignUpClick,
        text = stringResource(R.string.createAccount),
        enable = enable,
    )
}