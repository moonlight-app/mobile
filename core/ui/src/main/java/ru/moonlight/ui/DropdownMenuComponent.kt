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
fun DropdownMenuComponent(
    onSelected: (GenderOption) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String,
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
    var selectedChoose by remember { mutableStateOf<GenderOption?>(null) }
    val genderOptions = GenderOption.entries.toTypedArray()

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            onValueChange = {},
            modifier = modifier
                .menuAnchor(MenuAnchorType.PrimaryEditable),
            value = selectedChoose?.displayName ?: "",
            shape = MoonlightTheme.shapes.textFieldShape,
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
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
        )

        ExposedDropdownMenu(
            onDismissRequest = { expanded = false },
            expanded = expanded,
            containerColor = menuBackgroundColor,
        ) {
            genderOptions.forEach { gender ->
                DropdownMenuItem(
                    onClick = {
                        selectedChoose = gender
                        expanded = false
                        onSelected(gender)
                    },
                    text = gender.displayName,
                    backgroundColor = menuBackgroundColor,
                    textStyle = textStyle,
                    textColor = MoonlightTheme.colors.text,
                )
            }
        }
    }
}

@Composable
private fun DropdownMenuItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color,
    textStyle: TextStyle,
    textColor: Color,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = backgroundColor)
            .padding(10.dp)
            .clickable(onClick = onClick),
    ) {
        Text(
            modifier = Modifier,
            text = text,
            style = textStyle,
            color = textColor,
        )
    }
}