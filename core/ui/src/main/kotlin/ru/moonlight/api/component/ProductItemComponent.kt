package ru.moonlight.api.component

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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.progressbar.ProgressBarWidget
import ru.moonlight.api.widget.text.ButtonTextWidget
import ru.moonlight.api.widget.text.SubTitleTextWidget
import ru.moonlight.ui.R

@Composable
fun ProductItemComponent(
    onProductClick: (id: Long) -> Unit,
    onFavouriteClick: (id: Long, currentStatus: Boolean) -> Unit,
    id: Long,
    title: String,
    price: String,
    imageUrl: String,
    isFavorite: Boolean,
    isFavoriteButtonVisible: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .clickable { onProductClick(id) },
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
            ProductImageWithFavoriteButton(
                modifier = Modifier
                    .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal - 1.5.dp)
                    .padding(top = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical)
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(shape = MoonlightTheme.shapes.buttonShape)
                    .background(color = MoonlightTheme.colors.text),
                onFavouriteClick = { onFavouriteClick(id, isFavorite) },
                isFavorite = isFavorite,
                isFavoriteButtonVisible = isFavoriteButtonVisible,
                imageUrl = imageUrl,
            )
            TitleText(
                modifier = Modifier
                    .height(MoonlightTheme.typography.button.fontSize.value.dp * 3)
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
private fun ProductImageWithFavoriteButton(
    onFavouriteClick: () -> Unit,
    isFavorite: Boolean,
    isFavoriteButtonVisible: Boolean,
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = imageUrl,
            loading = {
                Box(
                    modifier = Modifier
                        .padding(MoonlightTheme.dimens.paddingFromEdges)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    ProgressBarWidget()
                }
            },
            error = {
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.ring),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Inside,
                )
            },
            contentDescription = "Product preview",
        )

        if (isFavoriteButtonVisible) {
            Box(
                modifier = Modifier.align(Alignment.TopEnd),
            ) {
                FavouriteButton(
                    modifier = Modifier
                        .padding(MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical/2),
                    onFavouriteClick = onFavouriteClick,
                    isFavourite = isFavorite
                )
            }
        }

    }

}

@Composable
private fun FavouriteButton(
    onFavouriteClick: () -> Unit,
    isFavourite: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .clip(shape = MoonlightTheme.shapes.checkBoxShape)
            .clickable { onFavouriteClick() },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(if (isFavourite) R.drawable.filled_favourite else R.drawable.favourite),
            contentDescription = "Favourite",
            tint = MoonlightTheme.colors.highlightText,
        )
    }
}

@Composable
private fun TitleText(
    title: String,
    modifier: Modifier = Modifier
) {
    ButtonTextWidget(
        modifier = modifier,
        text = title,
        textColor = MoonlightTheme.colors.text,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun PriceText(
    price: String,
    modifier: Modifier = Modifier,
) {
    SubTitleTextWidget(
        modifier = modifier,
        text = price.dropLast(2) + " ₽",
    )
}