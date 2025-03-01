package ru.moonlight.feature_auth_signin.sign_in.impl.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.textfield.LoginTextFieldWidget

@Composable
internal fun LoginTextField(
    onLoginChange: (String) -> Unit,
    email: String,
    enable: Boolean,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    LoginTextFieldWidget(
        modifier = modifier
            .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges)
            .padding(top = MoonlightTheme.dimens.paddingBetweenComponentsBigVertical),
        onFocusLost = onLoginChange,
        initialText = email,
        enable = enable,
        isError = isError,
    )
}