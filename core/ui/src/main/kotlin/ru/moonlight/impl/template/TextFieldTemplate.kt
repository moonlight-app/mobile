package ru.moonlight.impl.template

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.utils.keyboard.clearFocusOnKeyboardDismiss

@Composable
internal fun TextFieldTemplate(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String,
    focusedTextColor: Color,
    unfocusedTextColor: Color,
    focusedContainerColor: Color,
    focusedLabelColor: Color,
    unfocusedLabelColor: Color,
    focusedBorderColor: Color,
    unfocusedBorderColor: Color,
    errorBorderColor: Color,
    errorTextColor: Color,
    disabledBorderColor: Color,
    disabledTextColor: Color,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textStyle: TextStyle = MoonlightTheme.typography.textField,
    enable: Boolean = true,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .clearFocusOnKeyboardDismiss(),
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        textStyle = textStyle,
        enabled = enable,
        isError = isError,
        shape = MoonlightTheme.shapes.textFieldShape,
        colors = OutlinedTextFieldDefaults.colors(
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
        ),
        singleLine = singleLine,
        placeholder = {
            Text(
                text = placeholder,
                style = textStyle,
            )
        },
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
    )
}

@Composable
fun TextFieldWithSupportingTextTemplate(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions,
    enable: Boolean = true,
    isError: Boolean = false,
    errorText: String = "",
    focusedTextColor: Color,
    unfocusedTextColor: Color,
    focusedContainerColor: Color,
    focusedLabelColor: Color,
    unfocusedLabelColor: Color,
    focusedBorderColor: Color,
    unfocusedBorderColor: Color,
    errorBorderColor: Color,
    errorTextColor: Color,
    disabledBorderColor: Color,
    disabledTextColor: Color,
    textStyle: TextStyle = MoonlightTheme.typography.textField,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .clearFocusOnKeyboardDismiss(),
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        textStyle = textStyle,
        enabled = enable,
        isError = isError,
        shape = MoonlightTheme.shapes.textFieldShape,
        colors = OutlinedTextFieldDefaults.colors(
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
        ),
        singleLine = singleLine,
        placeholder = {
            Text(
                text = placeholder,
                style = textStyle,
            )
        },
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        supportingText = {
            if (isError && errorText.isNotEmpty()) {
                Text(
                    text = errorText,
                    style = textStyle,
                    color = errorTextColor,
                )
            }
        },
    )
}
