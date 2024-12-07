package ru.moonlight.ui

import androidx.compose.foundation.layout.fillMaxWidth
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
    onValueChange: (String) -> Unit,
    value: String,
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
    textStyle: TextStyle = MoonlightTheme.typography.textField,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        keyboardOptions = KeyboardOptions(
            capitalization = keyboardCapitalization,
            autoCorrectEnabled = true,
            keyboardType = keyboardType,
            imeAction = ImeAction.Next,
        ),
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
        placeholder = {
            Text(
                text = placeholder,
                style = textStyle,
            )
        },
    )
}

@Composable
fun TextFieldWithLabelComponent(
    onValueChange: (String) -> Unit,
    value: String,
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
    textStyle: TextStyle = MoonlightTheme.typography.textField,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        keyboardOptions = KeyboardOptions(
            capitalization = keyboardCapitalization,
            autoCorrectEnabled = true,
            keyboardType = keyboardType,
            imeAction = ImeAction.Next,
        ),
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
        }
    )
}

// remove when jetpack compose will support bottom padding 0.dp for supporting text
@Composable
fun TextFieldWithSupportingTextComponent(
    onValueChange: (String) -> Unit,
    value: String,
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
    textStyle: TextStyle = MoonlightTheme.typography.textField,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
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
        placeholder = {
            Text(
                text = placeholder,
                style = textStyle,
            )
        },
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
fun TextFieldPasswordComponent(
    onValueChange: (String) -> Unit,
    value: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Password,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    imeAction: ImeAction = ImeAction.Done,
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
    textStyle: TextStyle = MoonlightTheme.typography.textField,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
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

// remove when jetpack compose will support bottom padding 0.dp for supporting text
@Composable
fun TextFieldPasswordWithSupportingTextComponent(
    onValueChange: (String) -> Unit,
    value: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Password,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    imeAction: ImeAction = ImeAction.Done,
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

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
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
fun TextFieldOTP(
    onValueChange: (String) -> Unit,
    value: String,
    modifier: Modifier = Modifier,
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