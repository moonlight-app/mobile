package ru.moonlight.feature_auth_signin.sign_in.impl.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.textfield.PasswordTextFieldWithSupportingTextComponent

@Composable
internal fun PasswordTextField(
    onPasswordChange: (String) -> Unit,
    password: String,
    enable: Boolean,
    isError: Boolean,
    errorText: String,
    modifier: Modifier = Modifier,
) {
    PasswordTextFieldWithSupportingTextComponent(
        modifier = modifier
            .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges),
        onValueChange = onPasswordChange,
        value = password,
        enable = enable,
        isError = isError,
        errorText = errorText,
    )
}