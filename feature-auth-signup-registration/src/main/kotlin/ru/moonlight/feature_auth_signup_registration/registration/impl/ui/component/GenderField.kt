package ru.moonlight.feature_auth_signup_registration.registration.impl.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.component.GenderDropdownMenuComponent
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.common.GenderOption
import ru.moonlight.feature_auth_signup_registration.R

@Composable
internal fun GenderField(
    onGenderChoose: (GenderOption) -> Unit,
    gender: GenderOption?,
    enable: Boolean,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    GenderDropdownMenuComponent(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges),
        value = gender,
        onSelected = onGenderChoose,
        placeholder = stringResource(R.string.sex),
        enable = enable,
        isError = isError,
    )
}