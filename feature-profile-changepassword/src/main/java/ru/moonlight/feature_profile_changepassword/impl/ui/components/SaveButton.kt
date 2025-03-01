package ru.moonlight.feature_profile_changepassword.impl.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.widget.button.OutlinedButtonWidget
import ru.moonlight.feature_profile_changepassword.R

@Composable
internal fun ChangePasswordButton(
    onSaveClick: () -> Unit,
    enable: Boolean,
    modifier: Modifier = Modifier
) {
    OutlinedButtonWidget(
        modifier = modifier,
        onClick = onSaveClick,
        text = stringResource(R.string.changePassword),
        enable = enable,
    )
}