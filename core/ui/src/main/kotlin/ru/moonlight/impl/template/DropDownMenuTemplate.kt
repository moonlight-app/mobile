package ru.moonlight.impl.template

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.common.GenderOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DropdownMenuTemplate(
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