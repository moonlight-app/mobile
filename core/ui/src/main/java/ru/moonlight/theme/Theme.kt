package ru.moonlight.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

@Immutable
data class MoonlightThemeColors (
    val background: Color,
    val button: Color,
    val outlineButton: Color,
    val card: Color,
    val highlightText: Color,
    val text: Color,
    val hint: Color,
)

@Immutable
data class MoonlightThemeTypography(
    val title: TextStyle,
    val subtitle: TextStyle,
    val body: TextStyle,
    val description: TextStyle,
)

@Immutable
data class MoonlightThemeDimens(
    val card: Dp,
)

val LocalCustomColors = staticCompositionLocalOf {
    MoonlightThemeColors(
        background = Color.Unspecified,
        button = Color.Unspecified,
        outlineButton = Color.Unspecified,
        card = Color.Unspecified,
        highlightText = Color.Unspecified,
        text = Color.Unspecified,
        hint = Color.Unspecified
    )
}

val LocalCustomTypography = staticCompositionLocalOf {
    MoonlightThemeTypography (
        title = TextStyle.Default,
        subtitle = TextStyle.Default,
        body = TextStyle.Default,
        description = TextStyle.Default,
    )
}


@Composable
fun MoonlightTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    lateinit var customColors: MoonlightThemeColors
    lateinit var customTypography: MoonlightThemeTypography

    when {
        darkTheme -> {
            customColors = MoonlightThemeColors(
                background = DarkGray,
                button = LightBlue,
                outlineButton = DarkBlue,
                card = DarkOcean,
                highlightText = LightBlue,
                text = White,
                hint = White.copy(alpha = 0.5f)
            )

            customTypography = MoonlightThemeTypography (
                title = TextStyle(
                    color = White
                ),
                subtitle = TextStyle(
                    color = White
                ),
                body = TextStyle(
                    color = White
                ),
                description = TextStyle(
                    color = White
                ),
            )
        }
        else -> {
            //TODO("Add light theme in the future")
            customColors = MoonlightThemeColors(
                background = DarkGray,
                button = LightBlue,
                outlineButton = DarkBlue,
                card = DarkOcean,
                highlightText = LightBlue,
                text = White,
                hint = White.copy(alpha = 0.5f)
            )

            customTypography = MoonlightThemeTypography (
                title = TextStyle(
                    color = White
                ),
                subtitle = TextStyle(
                    color = White
                ),
                body = TextStyle(
                    color = White
                ),
                description = TextStyle(
                    color = White
                ),
            )
        }
    }

    CompositionLocalProvider(
        LocalCustomColors provides customColors,
        LocalCustomTypography provides customTypography,
        content = content
    )
}

object MoonlightTheme {
    val colors: MoonlightThemeColors
        @Composable
        get() = LocalCustomColors.current
    val typography: MoonlightThemeTypography
        @Composable
        get() = LocalCustomTypography.current
}