package ru.moonlight.api.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.text.SmallButtonTextWidget

@Composable
fun SnackbarComponent(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    SnackbarHost(
        modifier = modifier,
        hostState = snackbarHostState,
    ) { data ->
        Snackbar(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.07f),
            shape = MoonlightTheme.shapes.buttonShape,
            containerColor = MoonlightTheme.colors.error,
            contentColor = MoonlightTheme.colors.text,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MoonlightTheme.dimens.paddingBetweenComponentsHorizontal, Alignment.CenterHorizontally),
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null
                )
                SmallButtonTextWidget(
                    text = data.visuals.message,
                )
            }
        }
    }
}

