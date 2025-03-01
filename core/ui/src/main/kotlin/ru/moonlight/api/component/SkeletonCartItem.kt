package ru.moonlight.api.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import ru.moonlight.api.theme.MoonlightTheme

@Composable
fun SkeletonCartItem(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal)
            .clip(shape = MoonlightTheme.shapes.buttonShape)
            .background(color = MoonlightTheme.colors.card2)
            .padding(MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(
            MoonlightTheme.dimens.paddingBetweenComponentsHorizontal,
            alignment = Alignment.Start,
        )
    ) {
        SkeletonCheckBoxComponent(isTextVisible = false)
        ProductImageTemplate()
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(
                MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical/2,
            )
        ) {
            PriceTemplate()
            SizeTemplate()
        }
    }
}

@Composable
private fun ProductImageTemplate(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.5f)
            .height(132.dp)
            .clip(shape = MoonlightTheme.shapes.buttonShape)
            .shimmer()
            .background(color = Color.Gray),
    )
}

@Composable
private fun PriceTemplate(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(
                width = 72.dp,
                height = 20.dp,
            )
            .clip(shape = MoonlightTheme.shapes.buttonShape)
            .shimmer()
            .background(color = Color.Gray),
    )
}

@Composable
private fun SizeTemplate(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(
                width = 72.dp,
                height = 16.dp,
            )
            .clip(shape = MoonlightTheme.shapes.buttonShape)
            .shimmer()
            .background(color = Color.Gray),
    )
}