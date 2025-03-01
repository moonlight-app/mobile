package ru.moonlight.feature_profile_changepassword.impl.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.widget.textfield.PasswordTextFieldWithLabelWidget
import ru.moonlight.feature_profile_changepassword.R

@Composable
internal fun OldPasswordField(
    onPasswordChange: (String) -> Unit,
    password: String,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    PasswordTextFieldWithLabelWidget(
        modifier = modifier,
        onFocusLost = onPasswordChange,
        label = stringResource(R.string.oldPassword),
        initialText = password,
        isError = isError,
    )
}