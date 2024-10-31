package ru.moonlight.ui

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import ru.moonlight.theme.MoonlightTheme
import ru.moonlight.ui.otp.OTPTextField
import ru.moonlight.ui.otp.OtpTextFieldDefaults

@Composable
fun TextFieldComponent(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    isEnable: Boolean = true,
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
    textStyle: TextStyle = MoonlightTheme.typography.textField,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        keyboardOptions = KeyboardOptions(
            capitalization = keyboardCapitalization,
            autoCorrectEnabled = true,
            keyboardType = keyboardType,
            imeAction = ImeAction.Next,
        ),
        textStyle = textStyle,
        enabled = (isEnable),
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
    )
}

@Composable
fun TextFieldPasswordComponent(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Password,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    imeAction: ImeAction = ImeAction.Done,
    isEnable: Boolean = true,
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
    textStyle: TextStyle = MoonlightTheme.typography.textField,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        keyboardOptions = KeyboardOptions(
            capitalization = keyboardCapitalization,
            autoCorrectEnabled = true,
            keyboardType = keyboardType,
            imeAction = imeAction,
        ),
        textStyle = textStyle,
        enabled = (isEnable),
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
fun TextFieldOTP(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    containerColor: Color = Color.Transparent,
    focusedBorderColor: Color = MoonlightTheme.colors.highlightComponent,
    unfocusedBorderColor: Color = MoonlightTheme.colors.component,
    errorColor: Color = MoonlightTheme.colors.error,
    textColor: Color = MoonlightTheme.colors.text
) {
    OTPTextField(
        modifier = modifier,
        value = value,
        onTextChanged = onValueChange,
        numDigits = 6,
        isMasked = false,
        digitContainerStyle = OtpTextFieldDefaults.outlinedContainer(
            containerColor = containerColor,
            focusedBorderColor = focusedBorderColor,
            unfocusedBorderColor = unfocusedBorderColor,
            errorColor = errorColor,
        ),
        textStyle = MoonlightTheme.typography.textField.copy(color = textColor),
        isError = false,
    )
}