package ru.moonlight.api.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import ru.moonlight.api.theme.MoonlightTheme

@Composable
fun SkeletonCheckBoxComponent(
    modifier: Modifier = Modifier,
    isTextVisible: Boolean,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal,
        )
    ) {
        CheckBoxTemplate()
        if (isTextVisible) {
            TextWithCheckBox()
        }
    }
}

@Composable
private fun CheckBoxTemplate(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .clip(shape = MoonlightTheme.shapes.checkBoxShape)
            .shimmer()
            .background(color = Color.Gray),
    )
}

@Composable
private fun TextWithCheckBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(24.dp)
            .fillMaxWidth(0.3f)
            .clip(shape = MoonlightTheme.shapes.checkBoxShape)
            .shimmer()
            .background(color = Color.Gray),
    )
}