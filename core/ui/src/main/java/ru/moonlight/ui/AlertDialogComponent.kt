package ru.moonlight.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import ru.moonlight.theme.MoonlightTheme

@Composable
fun AlertDialogComponent(
    onConfirmExit: () -> Unit,
    onDismiss: () -> Unit,
    title: String,
    text: String,
    dismissText: String,
    confirmText: String,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = { Text(text = text) },
        confirmButton = {
            TextButton(
                onClick = onConfirmExit,
                colors = ButtonDefaults.textButtonColors(contentColor = MoonlightTheme.colors.text)
            ) { Text(text = confirmText) }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = MoonlightTheme.colors.highlightComponent)
            ) { Text(text = dismissText) }
        },
        containerColor = MoonlightTheme.colors.card,
        titleContentColor = MoonlightTheme.colors.text,
        textContentColor = MoonlightTheme.colors.hintText,
    )
}

@Composable
fun ChangePasswordDialog(
    onConfirm: (String, String) -> Unit, // Callback с двумя паролями: старым и новым
    onDismiss: () -> Unit,
    title: String,
    placeholder1: String,
    placeholder2: String,
    dismissText: String,
    confirmText: String,
) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextFieldPasswordComponent(
                    value = oldPassword,
                    onValueChange = { oldPassword = it },
                    placeholder = placeholder1,
                    singleLine = true,
                )
                TextFieldPasswordComponent(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    placeholder = placeholder2,
                    singleLine = true,
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(oldPassword, newPassword) },
                colors = ButtonDefaults.textButtonColors(contentColor = MoonlightTheme.colors.highlightComponent)
            ) { Text(text = confirmText) }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = MoonlightTheme.colors.text)
            ) { Text(text = dismissText) }
        },
        containerColor = MoonlightTheme.colors.card,
        titleContentColor = MoonlightTheme.colors.text,
        textContentColor = MoonlightTheme.colors.hintText,
    )
}