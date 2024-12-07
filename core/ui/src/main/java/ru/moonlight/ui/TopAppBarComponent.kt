package ru.moonlight.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import ru.moonlight.theme.MoonlightTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComponent(
    modifier: Modifier = Modifier,
    title: String,
    titleTextStyle: TextStyle = MoonlightTheme.typography.title,
    containerColor: Color = MoonlightTheme.colors.background,
    titleContentColor: Color = MoonlightTheme.colors.text,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = titleTextStyle,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            titleContentColor = titleContentColor,
        ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComponent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onActionClick: () -> Unit = {},
    title: String? = null,
    actionText: String? = null,
    titleTextStyle: TextStyle = MoonlightTheme.typography.title,
    actionTextStyle: TextStyle = MoonlightTheme.typography.button,
    containerColor: Color = MoonlightTheme.colors.background,
    titleContentColor: Color = MoonlightTheme.colors.text,
    navigationIconContentColor: Color = MoonlightTheme.colors.text,
    actionIconContentColor: Color = MoonlightTheme.colors.text,
) {
    CenterAlignedTopAppBar(
        modifier = modifier
            .padding(horizontal = 5.dp),
        title = {
            title?.let {
                Text(
                    text = title,
                    style = titleTextStyle,
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            actionText?.let {
                TextButton(
                    onClick = onActionClick,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = actionIconContentColor
                    )
                ) {
                    Text(
                        text = actionText,
                        style = actionTextStyle,
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            titleContentColor = titleContentColor,
            navigationIconContentColor = navigationIconContentColor,
            actionIconContentColor = actionIconContentColor,
        ),
    )
}