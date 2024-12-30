package ru.moonlight.feature_catalog.impl.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import ru.moonlight.theme.MoonlightTheme
import ru.moonlight.ui.ProgressBarComponent

@Composable
internal fun ProductItem(
    onProductClick: (Int) -> Unit,
    id: Int,
    title: String,
    price: String,
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .clickable { onProductClick(id) },
        shape = MoonlightTheme.shapes.buttonShape,
        border = BorderStroke(1.dp, color = MoonlightTheme.colors.disabledComponent),
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
            ProductPreview(
                modifier = Modifier
                    .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal - 1.5.dp)
                    .padding(top = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical)
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(shape = MoonlightTheme.shapes.buttonShape)
                    .background(color = MoonlightTheme.colors.text),
                imageUrl = imageUrl,
            )
            TitleText(
                modifier = Modifier
                    .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
                title = title,
            )
            PriceText(
                modifier = Modifier.padding(
                    start = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal,
                    bottom = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical,
                ),
                price = price,
            )
        }
    }
}

@Composable
private fun ProductPreview(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    SubcomposeAsyncImage(
        modifier = modifier,
        model = imageUrl,
        loading = {
            Box(
                modifier = Modifier
                    .padding(MoonlightTheme.dimens.paddingFromEdges)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ProgressBarComponent()
            }
        },
        error = {
            Image(
                bitmap = ImageBitmap.imageResource(ru.moonlight.ui.R.drawable.ring),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Inside,
            )
        },
        contentDescription = "Product preview",
    )
}

@Composable
private fun TitleText(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = title,
        color = MoonlightTheme.colors.text,
        style = MoonlightTheme.typography.button,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun PriceText(
    price: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = price.dropLast(2) + " ₽",
        color = MoonlightTheme.colors.text,
        style = MoonlightTheme.typography.subTitle,
    )
}
