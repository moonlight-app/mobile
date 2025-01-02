package ru.moonlight.api.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.impl.template.otp.OTPTemplate
import ru.moonlight.impl.template.otp.OtpTextFieldDefaults

@Composable
fun OTPComponent(
    onValueChange: (String) -> Unit,
    value: String,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Transparent,
    focusedBorderColor: Color = MoonlightTheme.colors.highlightComponent,
    unfocusedBorderColor: Color = MoonlightTheme.colors.component,
    errorColor: Color = MoonlightTheme.colors.error,
    textColor: Color = MoonlightTheme.colors.text
) {
    OTPTemplate(
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