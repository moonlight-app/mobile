package ru.moonlight.api.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import ru.moonlight.api.theme.MoonlightTheme

@Composable
fun SkeletonProductItem(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = MoonlightTheme.shapes.buttonShape,
        border = BorderStroke(1.dp, color = MoonlightTheme.colors.border),
        colors = CardDefaults.cardColors(
            containerColor = MoonlightTheme.colors.card2,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(
                MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
            ),
        ) {
            ImageTemplate()
            TitleTemplate()
            PriceTemplate()
        }
    }
}

@Composable
private fun ImageTemplate(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal - 1.5.dp)
            .padding(top = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical)
            .fillMaxWidth()
            .height(110.dp)
            .clip(shape = MoonlightTheme.shapes.buttonShape)
            .shimmer()
            .background(color = MoonlightTheme.colors.hintText),
    )
}

@Composable
private fun TitleTemplate(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(16.dp)
            .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal)
            .clip(shape = MoonlightTheme.shapes.buttonShape)
            .shimmer()
            .background(color = MoonlightTheme.colors.hintText),
    )
}

@Composable
private fun PriceTemplate(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(bottom = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical)
            .fillMaxWidth()
            .height(16.dp)
            .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal)
            .clip(shape = MoonlightTheme.shapes.buttonShape)
            .shimmer()
            .background(color = MoonlightTheme.colors.hintText),
    )
}