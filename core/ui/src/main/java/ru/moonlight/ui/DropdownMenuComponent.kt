package ru.moonlight.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
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
import androidx.compose.ui.unit.dp
import ru.moonlight.common.GenderOption
import ru.moonlight.theme.MoonlightTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownMenuTemplate(
    value: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onSelected: (GenderOption) -> Unit,
    options: Array<GenderOption>,
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    menuBackgroundColor: Color,
    outlinedTextField: @Composable (
        Modifier,
        Boolean,
        String,
        @Composable (() -> Unit)?
    ) -> Unit,
) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = onExpandedChange
    ) {
        outlinedTextField(
            Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryEditable),
            expanded,
            value,
        ) {
            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
        }

        ExposedDropdownMenu(
            onDismissRequest = { onExpandedChange(false) },
            expanded = expanded,
            containerColor = menuBackgroundColor,
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onExpandedChange(false)
                        onSelected(option)
                    },
                    text = option.displayName,
                    backgroundColor = menuBackgroundColor,
                    textStyle = textStyle,
                    textColor = MoonlightTheme.colors.text,
                )
            }
        }
    }
}

@Composable
fun DropdownMenuComponent(
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
fun DropdownMenuWithLabelComponent(
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


@Composable
private fun DropdownMenuItem(
    onClick: () -> Unit,
    text: String,
    backgroundColor: Color,
    textStyle: TextStyle,
    textColor: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = backgroundColor)
            .padding(10.dp)
            .clickable(onClick = onClick),
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor,
        )
    }
}