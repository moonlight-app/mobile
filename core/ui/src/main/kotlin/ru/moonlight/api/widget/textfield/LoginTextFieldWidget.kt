package ru.moonlight.api.widget.textfield

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.impl.template.TextFieldTemplate
import ru.moonlight.ui.R

@Composable
fun LoginTextFieldWidget(
    onValueChange: (String) -> Unit,
    value: String,
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(R.string.email),
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Email,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    enable: Boolean = true,
    isError: Boolean = false,
    focusedTextColor: Color = MoonlightTheme.colors.text,
    unfocusedTextColor: Color = MoonlightTheme.colors.text,
    focusedContainerColor: Color = Color.Transparent,
    focusedLabelColor: Color = MoonlightTheme.colors.highlightComponent,
    unfocusedLabelColor: Color = MoonlightTheme.colors.component,
    focusedBorderColor: Color = MoonlightTheme.colors.highlightComponent,
    unfocusedBorderColor: Color = MoonlightTheme.colors.component,
    errorBorderColor: Color = MoonlightTheme.colors.error,
    errorTextColor: Color = MoonlightTheme.colors.error,
    disabledBorderColor: Color = MoonlightTheme.colors.disabledComponent,
    disabledTextColor: Color = MoonlightTheme.colors.disabledText,
) {
    TextFieldTemplate(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = placeholder,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(
            capitalization = keyboardCapitalization,
            autoCorrectEnabled = true,
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),
        enable = enable,
        isError = isError,
        focusedTextColor = focusedTextColor,
        unfocusedTextColor = unfocusedTextColor,
        focusedContainerColor = focusedContainerColor,
        focusedLabelColor = focusedLabelColor,
        unfocusedLabelColor = unfocusedLabelColor,
        focusedBorderColor = focusedBorderColor,
        unfocusedBorderColor = unfocusedBorderColor,
        errorBorderColor = errorBorderColor,
        errorTextColor = errorTextColor,
        disabledBorderColor = disabledBorderColor,
        disabledTextColor = disabledTextColor,
    )
}