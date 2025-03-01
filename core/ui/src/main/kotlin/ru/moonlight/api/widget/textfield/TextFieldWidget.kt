package ru.moonlight.api.widget.textfield

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.impl.template.TextFieldTemplate
import ru.moonlight.impl.template.TextFieldWithLabelTemplate
import ru.moonlight.impl.template.TextFieldWithSupportingTextTemplate

@Composable
fun TextFieldWidget(
    onFocusLost: (String) -> Unit,
    onKeyboardDismiss: () -> Unit = {},
    initialText: String,
    modifier: Modifier = Modifier,
    placeholder: String,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
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
        onFocusLost = onFocusLost,
        onKeyboardDismiss = onKeyboardDismiss,
        initialText = initialText,
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

@Composable
fun TextFieldWithSupportingTextComponent(
    onFocusLost: (String) -> Unit,
    onKeyboardDismiss: () -> Unit = {},
    initialText: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    enable: Boolean = true,
    isError: Boolean = false,
    errorText: String = "",
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
    TextFieldWithSupportingTextTemplate(
        onFocusLost = onFocusLost,
        onKeyboardDismiss = onKeyboardDismiss,
        initialText = initialText,
        placeholder = placeholder,
        modifier = modifier,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(
            capitalization = keyboardCapitalization,
            autoCorrect = true,
            keyboardType = keyboardType,
            imeAction = ImeAction.Next,
        ),
        enable = enable,
        isError = isError,
        errorText = errorText,
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

@Composable
fun TextFieldWithLabelWidget(
    onFocusLost: (String) -> Unit,
    onKeyboardDismiss: () -> Unit = {},
    initialText: String,
    modifier: Modifier = Modifier,
    label: String,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
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
    TextFieldWithLabelTemplate(
        onFocusLost = onFocusLost,
        onKeyboardDismiss = onKeyboardDismiss,
        initialText = initialText,
        label = label,
        modifier = modifier,
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