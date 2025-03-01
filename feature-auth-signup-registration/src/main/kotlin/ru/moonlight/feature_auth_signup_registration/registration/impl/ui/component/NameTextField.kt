package ru.moonlight.feature_auth_signup_registration.registration.impl.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.textfield.TextFieldWidget
import ru.moonlight.feature_auth_signup_registration.R

@Composable
internal fun NameTextField(
    onNameChange: (String) -> Unit,
    name: String,
    enable: Boolean,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    TextFieldWidget(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges)
            .padding(top = MoonlightTheme.dimens.paddingBetweenComponentsBigVertical),
        onFocusLost = onNameChange,
        initialText = name,
        placeholder = stringResource(R.string.name),
        enable = enable,
        isError = isError,
    )
}