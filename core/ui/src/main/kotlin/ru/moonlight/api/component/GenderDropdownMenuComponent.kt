package ru.moonlight.api.component

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
import androidx.compose.ui.text.TextStyle
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.common.GenderOption
import ru.moonlight.impl.template.DropdownMenuTemplate

@Composable
fun GenderDropdownMenuComponent(
    onSelected: (GenderOption) -> Unit,
    value: GenderOption?,
    placeholder: String,
    modifier: Modifier = Modifier,
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
    menuBackgroundColor: Color = MoonlightTheme.colors.card,
) {
    var expanded by remember { mutableStateOf(false) }
    val genderOptions = GenderOption.entries.toTypedArray()

    DropdownMenuTemplate(
        value = value?.displayName ?: "",
        expanded = expanded,
        onExpandedChange = { expanded = it },
        onSelected = onSelected,
        options = genderOptions,
        modifier = modifier,
        textStyle = textStyle,
        menuBackgroundColor = menuBackgroundColor,
        outlinedTextField = { mod, _, text, trailingIcon ->
            OutlinedTextField(
                value = text,
                onValueChange = {},
                modifier = mod,
                readOnly = true,
                textStyle = textStyle,
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
                placeholder = {
                    Text(
                        text = placeholder,
                        style = textStyle,
                    )
                },
                trailingIcon = trailingIcon,
                enabled = enable,
                isError = isError,
                shape = MoonlightTheme.shapes.textFieldShape,
            )
        }
    )
}

@Composable
fun GenderDropdownMenuWithLabelComponent(
    onSelected: (GenderOption) -> Unit,
    value: GenderOption?,
    modifier: Modifier = Modifier,
    label: String,
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
    menuBackgroundColor: Color = MoonlightTheme.colors.card,
) {
    var expanded by remember { mutableStateOf(false) }
    val genderOptions = GenderOption.entries.toTypedArray()

    DropdownMenuTemplate(
        value = value?.displayName ?: "",
        expanded = expanded,
        onExpandedChange = { expanded = it },
        onSelected = onSelected,
        options = genderOptions,
        modifier = modifier,
        textStyle = textStyle,
        menuBackgroundColor = menuBackgroundColor,
        outlinedTextField = { mod, _, text, trailingIcon ->
            OutlinedTextField(
                value = text,
                onValueChange = {},
                modifier = mod,
                readOnly = true,
                textStyle = textStyle,
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
                label = {
                    Text(
                        text = label,
                        style = textStyle,
                    )
                },
                trailingIcon = trailingIcon,
                enabled = enable,
                isError = isError,
                shape = MoonlightTheme.shapes.textFieldShape,
            )
        }
    )
}


