package ru.moonlight.feature_profile_edit.impl.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.textfield.TextFieldWithLabelWidget
import ru.moonlight.feature_profile_edit.R

@Composable
internal fun NameTextField(
    onNameChange: (String) -> Unit,
    name: String,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    TextFieldWithLabelWidget(
        modifier = modifier
            .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
        onFocusLost = onNameChange,
        initialText = name,
        label = stringResource(R.string.name),
        isError = isError,
    )
}