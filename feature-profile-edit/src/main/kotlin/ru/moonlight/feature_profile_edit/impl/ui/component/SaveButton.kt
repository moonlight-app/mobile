package ru.moonlight.feature_profile_edit.impl.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.widget.button.OutlinedButtonWidget
import ru.moonlight.feature_profile_edit.R

@Composable
internal fun SaveButton(
    onSaveClick: () -> Unit,
    enable: Boolean,
    modifier: Modifier = Modifier
) {
    OutlinedButtonWidget(
        modifier = modifier,
        onClick = onSaveClick,
        text = stringResource(R.string.save),
        enable = enable,
    )
}