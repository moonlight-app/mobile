package ru.moonlight.feature_auth_signup_registration.registration.impl.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.textfield.LoginTextFieldWidget

@Composable
internal fun LoginTextField(
    onLoginChange: (String) -> Unit,
    login: String,
    enable: Boolean,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    LoginTextFieldWidget(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges),
        onFocusLost = onLoginChange,
        initialText = login,
        enable = enable,
        isError = isError,
    )
}

