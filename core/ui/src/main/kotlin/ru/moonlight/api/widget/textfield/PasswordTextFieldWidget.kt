package ru.moonlight.api.widget.textfield

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.impl.template.TextFieldTemplate
import ru.moonlight.impl.template.TextFieldWithLabelTemplate
import ru.moonlight.impl.template.TextFieldWithSupportingTextAndLabelTemplate
import ru.moonlight.impl.template.TextFieldWithSupportingTextTemplate
import ru.moonlight.ui.R

@Composable
fun PasswordTextFieldWidget(
    onFocusLost: (String) -> Unit,
    onKeyboardDismiss: () -> Unit = {},
    initialText: String = "",
    placeholder: String = stringResource(R.string.password),
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    enable: Boolean = true,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Password,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.None,
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
    textStyle: TextStyle = MoonlightTheme.typography.textField,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextFieldTemplate(
        modifier = modifier,
        onFocusLost = onFocusLost,
        onKeyboardDismiss = onKeyboardDismiss,
        initialText = initialText,
        placeholder = placeholder,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(
            capitalization = keyboardCapitalization,
            autoCorrectEnabled = false,
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        textStyle = textStyle,
        enable = enable,
        isError = isError,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onClick = { passwordVisible = !passwordVisible },
            ) {
                Icon(
                    painter = if (passwordVisible) painterResource(id = R.drawable.hide_no) else painterResource(id = R.drawable.hide_yes),
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    tint = MoonlightTheme.colors.hintText,
                )
            }
        },
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
fun PasswordTextFieldWithLabelWidget(
    onFocusLost: (String) -> Unit,
    onKeyboardDismiss: () -> Unit = {},
    initialText: String,
    modifier: Modifier = Modifier,
    label: String,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Password,
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
    var passwordVisible by remember { mutableStateOf(false) }

    TextFieldWithLabelTemplate(
        onFocusLost = onFocusLost,
        onKeyboardDismiss = onKeyboardDismiss,
        initialText = initialText,
        label = label,
        modifier = modifier,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(
            capitalization = keyboardCapitalization,
            autoCorrectEnabled = false,
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),
        enable = enable,
        isError = isError,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onClick = { passwordVisible = !passwordVisible },
            ) {
                Icon(
                    painter = if (passwordVisible) painterResource(id = R.drawable.hide_no) else painterResource(id = R.drawable.hide_yes),
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    tint = MoonlightTheme.colors.hintText,
                )
            }
        },
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
fun PasswordTextFieldWithSupportingTextWidget(
    onFocusLost: (String) -> Unit,
    onKeyboardDismiss: () -> Unit = {},
    initialValue: String = "",
    placeholder: String = stringResource(R.string.password),
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    enable: Boolean = true,
    isError: Boolean = false,
    errorText: String = "",
    keyboardType: KeyboardType = KeyboardType.Password,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.None,
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
    textStyle: TextStyle = MoonlightTheme.typography.textField,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextFieldWithSupportingTextTemplate(
        onFocusLost = onFocusLost,
        onKeyboardDismiss = onKeyboardDismiss,
        initialText = initialValue,
        placeholder = placeholder,
        modifier = modifier,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(
            capitalization = keyboardCapitalization,
            autoCorrectEnabled = false,
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
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
        textStyle = textStyle,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onClick = { passwordVisible = !passwordVisible },
            ) {
                Icon(
                    painter = if (passwordVisible) painterResource(id = R.drawable.hide_no) else painterResource(id = R.drawable.hide_yes),
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    tint = MoonlightTheme.colors.hintText,
                )
            }
        },
    )
}

@Composable
fun PasswordTextFieldWithSupportingTextAndLabelWidget(
    onFocusLost: (String) -> Unit,
    onKeyboardDismiss: () -> Unit = {},
    initialValue: String = "",
    label: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
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
    textStyle: TextStyle = MoonlightTheme.typography.textField,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextFieldWithSupportingTextAndLabelTemplate(
        onFocusLost = onFocusLost,
        onKeyboardDismiss = onKeyboardDismiss,
        initialText = initialValue,
        label = label,
        modifier = modifier,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            autoCorrectEnabled = true,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
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
        textStyle = textStyle,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onClick = { passwordVisible = !passwordVisible },
            ) {
                Icon(
                    painter = if (passwordVisible) painterResource(id = R.drawable.hide_no) else painterResource(id = R.drawable.hide_yes),
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    tint = MoonlightTheme.colors.hintText,
                )
            }
        },
    )
}