package ru.moonlight.impl.template

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.utils.keyboard.clearFocusOnKeyboardDismiss

@Composable
internal fun TextFieldTemplate(
    onFocusLost: (String) -> Unit,
    onKeyboardDismiss: () -> Unit,
    initialText: String,
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
    var text by rememberSaveable { mutableStateOf(initialText) }
    var isFocused by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .clearFocusOnKeyboardDismiss(onKeyboardDismiss = onKeyboardDismiss)
            .onFocusChanged { focusState ->
                if (isFocused && !focusState.isFocused) {
                    onFocusLost(text)
                }
                isFocused = focusState.isFocused
            },
        value = text,
        onValueChange = { text = it },
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
internal fun TextFieldWithSupportingTextTemplate(
    onFocusLost: (String) -> Unit,
    onKeyboardDismiss: () -> Unit,
    initialText: String,
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
    var text by rememberSaveable { mutableStateOf(initialText) }
    var isFocused by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .clearFocusOnKeyboardDismiss(onKeyboardDismiss = onKeyboardDismiss)
            .onFocusChanged { focusState ->
                if (isFocused && !focusState.isFocused) {
                    onFocusLost(text)
                }
                isFocused = focusState.isFocused
            },
        value = text,
        onValueChange = { text = it},
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

@Composable
internal fun TextFieldWithSupportingTextAndLabelTemplate(
    onFocusLost: (String) -> Unit,
    onKeyboardDismiss: () -> Unit,
    initialText: String,
    label: String,
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
    var text by rememberSaveable { mutableStateOf(initialText) }
    var isFocused by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .clearFocusOnKeyboardDismiss(onKeyboardDismiss = onKeyboardDismiss)
            .onFocusChanged { focusState ->
                if (isFocused && !focusState.isFocused) {
                    onFocusLost(text)
                }
                isFocused = focusState.isFocused
            },
        value = text,
        onValueChange = { text = it},
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
        label = {
            Text(
                text = label,
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

@Composable
internal fun TextFieldWithLabelTemplate(
    onFocusLost: (String) -> Unit,
    onKeyboardDismiss: () -> Unit = {},
    initialText: String,
    modifier: Modifier = Modifier,
    label: String,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions,
    enable: Boolean = true,
    isError: Boolean = false,
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
    var text by rememberSaveable { mutableStateOf(initialText) }
    var isFocused by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .clearFocusOnKeyboardDismiss(onKeyboardDismiss = onKeyboardDismiss)
            .onFocusChanged { focusState ->
                if (isFocused && !focusState.isFocused) {
                    onFocusLost(text)
                }
                isFocused = focusState.isFocused
            },
        value = text,
        onValueChange = { text = it },
        keyboardOptions = keyboardOptions,
        textStyle = textStyle,
        enabled = (enable),
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
        label = {
            Text(
                text = label,
                style = textStyle,
            )
        },
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
    )
}