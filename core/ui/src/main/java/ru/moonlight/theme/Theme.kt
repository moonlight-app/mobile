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
    val component: Color,
    val highlightComponent: Color,
    val outlineHighlightComponent: Color,
    val card: Color,
    val highlightText: Color,
    val text: Color,
    val hintText: Color,
    val disabledComponent: Color,
    val disabledText: Color,
    val error: Color,
)

@Immutable
data class MoonlightThemeTypography(
    val title: TextStyle,
    val subTitle: TextStyle,
    val secondTitle: TextStyle,
    val textField: TextStyle,
    val button: TextStyle,
    val smallButton: TextStyle,
    val description: TextStyle,
)

@Immutable
data class MoonlightThemeDimens(
    /* height/width */
    val buttonHeight: Dp,
    val smallButtonHeight: Dp,
    val textFieldHeight: Dp,
    val secondTextFieldHeight: Dp,
    val buttonBorderWidth: Dp,
    val progressBarWidth: Dp,
    val smallProgressBarWidth: Dp,

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

    /* padding */
    val paddingFromEdges: Dp,
    val paddingBetweenComponentsHorizontal: Dp,
    val paddingBetweenComponentsSmallVertical: Dp,
    val paddingBetweenComponentsBigVertical: Dp,
)

@Immutable
data class MoonlightThemeShapes(
    val buttonShape: RoundedCornerShape,
    val textFieldShape: RoundedCornerShape,
)

internal val LocalCustomColors = staticCompositionLocalOf {
    MoonlightThemeColors(
        background = Color.Unspecified,
        component = Color.Unspecified,
        highlightComponent = Color.Unspecified,
        outlineHighlightComponent = Color.Unspecified,
        card = Color.Unspecified,
        highlightText = Color.Unspecified,
        text = Color.Unspecified,
        hintText = Color.Unspecified,
        disabledComponent = Color.Unspecified,
        disabledText = Color.Unspecified,
        error = Color.Unspecified,
    )
}

internal val LocalCustomTypography = staticCompositionLocalOf {
    MoonlightThemeTypography (
        title = TextStyle.Default,
        subTitle = TextStyle.Default,
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
        buttonShape = RoundedCornerShape(24.dp),
        textFieldShape = RoundedCornerShape(50.dp),
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
            component = Gray30,
            highlightComponent = LightBlue,
            outlineHighlightComponent = DarkBlue,
            card = DarkOcean,
            highlightText = DarkBlue,
            text = White,
            hintText = Gray60,
            disabledComponent = Gray60,
            disabledText = Gray30,
            error = Red,
        )
    } else {
        //TODO("Add light theme in the future")
        MoonlightThemeColors(
            background = DarkGray,
            component = Gray30,
            highlightComponent = LightBlue,
            outlineHighlightComponent = DarkBlue,
            card = DarkOcean,
            highlightText = DarkBlue,
            text = White,
            hintText = Gray60,
            disabledComponent = Gray60,
            disabledText = Gray30,
            error = Red,
        )
    }

    val customTypography = MoonlightThemeTypography (
        title = titleTextStyle,
        subTitle = subTitleTextStyle,
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
        buttonShape = RoundedCornerShape(customDimens.buttonRadius),
        textFieldShape = RoundedCornerShape(customDimens.textFieldRadius),
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