package ru.moonlight.api.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import ru.moonlight.api.theme.MoonlightTheme

@Composable
fun ConfirmExitAlertDialogComponent(
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