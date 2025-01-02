package ru.moonlight.feature_profile_edit.impl.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.component.GenderDropdownMenuWithLabelComponent
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.common.GenderOption
import ru.moonlight.feature_profile_edit.R

@Composable
internal fun GenderField(
    onGenderChoose: (GenderOption) -> Unit,
    gender: GenderOption?,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    GenderDropdownMenuWithLabelComponent(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
        value = gender,
        onSelected = onGenderChoose,
        label = stringResource(R.string.sex),
        isError = isError,
    )
}