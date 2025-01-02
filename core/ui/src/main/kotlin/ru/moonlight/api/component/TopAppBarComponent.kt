package ru.moonlight.api.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.text.ButtonTextWidget
import ru.moonlight.api.widget.text.TitleTextWidget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComponent(
    modifier: Modifier = Modifier,
    title: String,
    containerColor: Color = MoonlightTheme.colors.background,
    titleContentColor: Color = MoonlightTheme.colors.text,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            TitleTextWidget(
                text = title,
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
    actionVisibility: Boolean = false,
    title: String? = null,
    actionText: String? = null,
    containerColor: Color = MoonlightTheme.colors.background,
    titleContentColor: Color = MoonlightTheme.colors.text,
    navigationIconContentColor: Color = MoonlightTheme.colors.text,
    actionIconContentColor: Color = MoonlightTheme.colors.text,
    content: @Composable (() -> Unit) = {},
) {
    Column {
        CenterAlignedTopAppBar(
            modifier = modifier
                .padding(horizontal = 5.dp),
            title = {
                Crossfade(targetState = !actionVisibility, label = "AppBarAnimation") { isVisible ->
                    title?.let {
                        (if (isVisible) title else actionText)?.let { text ->
                            TitleTextWidget(
                                text = text,
                            )
                        }
                    }
                }
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Crossfade(
                        targetState = actionVisibility,
                        animationSpec = tween(durationMillis = 400),
                        label = "AppBarAnimation"
                    ) { isVisible ->
                        if (isVisible) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close"
                            )
                        } else {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            },
            actions = {
                AnimatedVisibility(
                    visible = !actionVisibility,
                    enter = fadeIn(animationSpec = tween(durationMillis = 250)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 250))
                ) {
                    actionText?.let {
                        TextButton(
                            onClick = onActionClick,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = actionIconContentColor
                            )
                        ) {
                            ButtonTextWidget(
                                text = actionText,
                            )
                        }
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

        androidx.compose.animation.AnimatedVisibility(
            visible = actionVisibility,
            enter = slideInVertically() + expandVertically(
                expandFrom = Alignment.Top
            ) + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            content()
        }

    }
}