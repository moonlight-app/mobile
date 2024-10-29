package ru.moonlight.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Immutable
data class MoonlightThemeColors (
    val background: Color,
    val button: Color,
    val outlineButton: Color,
    val card: Color,
    val highlightText: Color,
    val text: Color,
    val hint: Color,
    val disabledButton: Color,
    val disabledText: Color,
    val error: Color,
)

@Immutable
data class MoonlightThemeTypography(
    val title: TextStyle,
    val secondTitle: TextStyle,
    val textField: TextStyle,
    val button: TextStyle,
    val smallButton: TextStyle,
    val description: TextStyle,
)

@Immutable
data class MoonlightThemeDimens(
    val buttonHeight: Dp,
    val smallButtonHeight: Dp,
    val textFieldHeight: Dp,
    val secondTextFieldHeight: Dp,

    /* corner radius */
    val textFieldRadius: Dp,
    val secondTextFieldRadius: Dp,
    val buttonRadius: Dp,
    val cardRadius: Dp,

    /* size */
    val progressBarSize: Dp,
    val smallProgressBarSize: Dp,

    /* font size */
    val titleTextSize: TextUnit,
    val textFieldFontSize: TextUnit,
    val secondTextFieldFontSize: TextUnit,
    val buttonFontSize: TextUnit,
    val smallButtonFontSize: TextUnit,
    val descriptionFontSize: TextUnit,
)

@Immutable
data class MoonlightThemeShapes(
    val buttonShape: RoundedCornerShape,
)

internal val LocalCustomColors = staticCompositionLocalOf {
    MoonlightThemeColors(
        background = Color.Unspecified,
        button = Color.Unspecified,
        outlineButton = Color.Unspecified,
        card = Color.Unspecified,
        highlightText = Color.Unspecified,
        text = Color.Unspecified,
        hint = Color.Unspecified,
        disabledButton = Color.Unspecified,
        disabledText = Color.Unspecified,
        error = Color.Unspecified,
    )
}

internal val LocalCustomTypography = staticCompositionLocalOf {
    MoonlightThemeTypography (
        title = TextStyle.Default,
        secondTitle = TextStyle.Default,
        textField = TextStyle.Default,
        button = TextStyle.Default,
        smallButton = TextStyle.Default,
        description = TextStyle.Default,
    )
}

internal val LocalCustomDimens = staticCompositionLocalOf {
    CompactSmallDimens
}

internal val LocalCustomShapes = staticCompositionLocalOf {
    MoonlightThemeShapes(
        buttonShape = RoundedCornerShape(24.dp)
    )
}


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MoonlightTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    activity: Activity = LocalContext.current as Activity,
    content: @Composable () -> Unit
) {
    val customColors: MoonlightThemeColors = if (darkTheme) {
        MoonlightThemeColors(
            background = DarkGray,
            button = LightBlue,
            outlineButton = DarkBlue,
            card = DarkOcean,
            highlightText = LightBlue,
            text = White,
            hint = White.copy(alpha = 0.5f),
            disabledButton = Gray60,
            disabledText = Gray30,
            error = Red,
        )
    } else {
        //TODO("Add light theme in the future")
        MoonlightThemeColors(
            background = DarkGray,
            button = LightBlue,
            outlineButton = DarkBlue,
            card = DarkOcean,
            highlightText = LightBlue,
            text = White,
            hint = White.copy(alpha = 0.5f),
            disabledButton = Gray60,
            disabledText = Gray30,
            error = Red,
        )
    }

    val customTypography = MoonlightThemeTypography (
        title = titleTextStyle,
        secondTitle = secondTitleTextStyle,
        textField = textFieldTextStyle,
        button = buttonTextStyle,
        smallButton = smallButtonTextStyle,
        description = descriptionTextStyle,
    )

    var customDimens: MoonlightThemeDimens = CompactSmallDimens

    val window = calculateWindowSizeClass(activity = activity)
    val config = LocalConfiguration.current
    when (window.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            if (config.screenWidthDp <= 360) {
                customDimens = CompactSmallDimens
            }
            else if (config.screenWidthDp <= 599) {
                customDimens = CompactMediumDimens
            }
            else {
                customDimens = CompactDimens
            }
        }
//        WindowWidthSizeClass.Medium -> {
//
//        }
//        WindowWidthSizeClass.Expanded -> {
//
//        }
    }

    val customShapes = MoonlightThemeShapes(
        buttonShape = RoundedCornerShape(customDimens.buttonRadius)
    )

    CompositionLocalProvider(
        LocalCustomColors provides customColors,
        LocalCustomTypography provides customTypography,
        LocalCustomDimens provides customDimens,
        LocalCustomShapes provides customShapes,
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
    val dimens: MoonlightThemeDimens
        @Composable
        get() = LocalCustomDimens.current
    val shapes: MoonlightThemeShapes
        @Composable
        get() = LocalCustomShapes.current
}