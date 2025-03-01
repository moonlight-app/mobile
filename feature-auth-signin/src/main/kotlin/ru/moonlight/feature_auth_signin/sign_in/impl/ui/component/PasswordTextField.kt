package ru.moonlight.feature_auth_signin.sign_in.impl.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.textfield.PasswordTextFieldWithSupportingTextWidget

@Composable
internal fun PasswordTextField(
    onPasswordChange: (String) -> Unit,
    password: String,
    enable: Boolean,
    isError: Boolean,
    errorText: String,
    modifier: Modifier = Modifier,
) {
    PasswordTextFieldWithSupportingTextWidget(
        modifier = modifier
            .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges),
        onFocusLost = onPasswordChange,
        initialValue = password,
        enable = enable,
        isError = isError,
        errorText = errorText,
    )
}