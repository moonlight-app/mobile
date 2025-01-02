package ru.moonlight.api.widget.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.text.SmallButtonTextWidget

@Composable
fun ButtonChipWidget(
    onClick: () -> Unit,
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Transparent,
    selectedContainerColor: Color = MoonlightTheme.colors.highlightComponent,
    labelColor: Color = MoonlightTheme.colors.outlineHighlightComponent,
    selectedLabelColor: Color = MoonlightTheme.colors.text,
    animationDuration: Int = 200
) {
    val animatedContainerColor by animateColorAsState(
        targetValue = if (selected) selectedContainerColor else containerColor,
        animationSpec = tween(durationMillis = animationDuration),
        label = ""
    )

    val animatedBorderColor by animateColorAsState(
        targetValue = if (selected) containerColor else labelColor,
        animationSpec = tween(durationMillis = animationDuration),
        label = ""
    )

    val animatedTextColor by animateColorAsState(
        targetValue = if (selected) selectedLabelColor else labelColor,
        animationSpec = tween(durationMillis = animationDuration),
        label = ""
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = animatedContainerColor,
                shape = MoonlightTheme.shapes.buttonShape
            )
            .border(
                width = 1.dp,
                color = animatedBorderColor,
                shape = MoonlightTheme.shapes.buttonShape
            )
            .clip(shape = MoonlightTheme.shapes.buttonShape)
            .clickable(onClick = onClick)
            .padding(
                horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal,
                vertical = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
            )
    ) {
        SmallButtonTextWidget(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical / 2),
            text = text,
            textColor = animatedTextColor,
        )
    }
}