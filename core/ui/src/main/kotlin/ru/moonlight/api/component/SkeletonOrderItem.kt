package ru.moonlight.api.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import ru.moonlight.api.theme.MoonlightTheme

@Composable
fun SkeletonOrderItem(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                1.dp,
                color = MoonlightTheme.colors.border,
                shape = MoonlightTheme.shapes.buttonShape,
            )
            .clip(shape = MoonlightTheme.shapes.buttonShape)
            .wrapContentHeight()
            .padding(MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
        horizontalArrangement = Arrangement.spacedBy(
            space = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal,
            alignment = Alignment.Start,
        ),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(52.dp, 42.dp)
                .clip(MoonlightTheme.shapes.textFieldShape)
                .shimmer()
                .background(color = Color.Gray)
        )
        Column(
            modifier = Modifier
                .height(42.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .clip(MoonlightTheme.shapes.textFieldShape)
                    .shimmer()
                    .background(Color.Gray),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .height(12.dp)
                    .clip(MoonlightTheme.shapes.textFieldShape)
                    .shimmer()
                    .background(Color.Gray),
            )
        }
    }
}