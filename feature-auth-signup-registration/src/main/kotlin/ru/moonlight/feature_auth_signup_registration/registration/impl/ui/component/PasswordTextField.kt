package ru.moonlight.feature_auth_signup_registration.registration.impl.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.moonlight.api.widget.textfield.PasswordTextFieldWithSupportingTextComponent
import ru.moonlight.api.theme.MoonlightTheme

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
            .fillMaxWidth()
            .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges)
            .imePadding(),
        onValueChange = { newValue -> onPasswordChange(newValue) },
        value = password,
        enable = enable,
        isError = isError,
        errorText = errorText,
    )
}