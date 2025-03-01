package ru.moonlight.feature_profile_changepassword.impl.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.widget.textfield.PasswordTextFieldWithSupportingTextAndLabelWidget
import ru.moonlight.feature_profile_changepassword.R

@Composable
internal fun ConfirmNewPasswordField(
    isNewPasswordEqualsConfirmPassword: (Boolean) -> Unit,
    password: String,
    newPassword: String,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    PasswordTextFieldWithSupportingTextAndLabelWidget(
        modifier = modifier,
        onFocusLost = { confirmPassword ->
            isNewPasswordEqualsConfirmPassword(confirmPassword != newPassword)
        },
        label = stringResource(R.string.confirmPassword),
        initialValue = password,
        isError = isError,
        errorText = stringResource(R.string.oldPasswordNotEqualsToNewPassword),
    )
}