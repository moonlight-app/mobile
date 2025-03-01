package ru.moonlight.api.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.CheckBoxWidget
import ru.moonlight.api.widget.progressbar.ProgressBarWidget
import ru.moonlight.api.widget.text.SubTitleTextWidget
import ru.moonlight.api.widget.text.TextFieldTextWidget
import ru.moonlight.ui.R

@Composable
fun CartFeedItemComponent(
    onProductClick: (id: Long) -> Unit,
    onCheckedChange:(Long, Boolean) -> Unit,
    onDeleteClick:(Long) -> Unit,
    onFavouriteClick:(id: Long, currentStatus: Boolean) -> Unit,
    onPlusClick:(id: Long, count: Int) -> Unit,
    onMinusClick:(id: Long, count: Int) -> Unit,
    cartFeedModel: CartFeedModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = MoonlightTheme.shapes.buttonShape)
            .background(color = MoonlightTheme.colors.card2)
            .padding(MoonlightTheme.dimens.paddingBetweenComponentsHorizontal)
            .clickable { onProductClick(cartFeedModel.productId) },
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(
            space = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal,
            alignment = Alignment.Start,
        ),
    ) {
        CheckBoxWidget(
            modifier = Modifier,
            checked = cartFeedModel.chosen,
            onCheckedChange = { onCheckedChange(cartFeedModel.itemId, !cartFeedModel.chosen) },
        )
        ProductImageWithFavoriteButton(
            modifier = Modifier,
            imageUrl = cartFeedModel.imageUrl,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(132.dp),
            verticalArrangement = Arrangement.spacedBy(
                MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical/2,
            )
        ) {
            SubTitleTextWidget(text = "${cartFeedModel.price.dropLast(2)} ₽")
            if (cartFeedModel.size != null) TextFieldTextWidget(text = "${cartFeedModel.size} ${stringResource(R.string.size)}")
            CounterOfItem(
                onIncreaseClick = { count -> onPlusClick(cartFeedModel.itemId, count) },
                onDecreaseClick = { count -> onMinusClick(cartFeedModel.itemId, count) },
                count = cartFeedModel.count,
            )
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        space = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical/2
                    )
                ) {
                    FavouriteButton(
                        onFavouriteClick = { onFavouriteClick(cartFeedModel.productId, cartFeedModel.isFavourite) },
                        isFavourite = cartFeedModel.isFavourite,
                    )
                    DeleteButton(
                        onDeleteClick = { onDeleteClick(cartFeedModel.itemId) },
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductImageWithFavoriteButton(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    SubcomposeAsyncImage(
        modifier = modifier
            .clip(shape = MoonlightTheme.shapes.buttonShape)
            .fillMaxWidth(0.5f)
            .height(132.dp),
        model = imageUrl,
        loading = {
            Box(
                modifier = Modifier
                    .padding(MoonlightTheme.dimens.paddingFromEdges)
                    .height(132.dp),
                contentAlignment = Alignment.Center
            ) {
                ProgressBarWidget()
            }
        },
        error = {
            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.ring),
                contentDescription = null,
                modifier = Modifier
                    .height(132.dp)
                    .clip(shape = MoonlightTheme.shapes.buttonShape)
                    .background(color = Color.Gray),
                contentScale = ContentScale.Inside,
            )
        },
        contentScale = ContentScale.Crop,
        contentDescription = "Product preview",
    )
}

@Composable
private fun CounterOfItem(
    onDecreaseClick: (Int) -> Unit,
    onIncreaseClick: (Int) -> Unit,
    count: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(top = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical/2),
        horizontalArrangement = Arrangement.spacedBy(
            MoonlightTheme.dimens.paddingBetweenComponentsHorizontal,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(shape = MoonlightTheme.shapes.checkBoxShape)
                .background(MoonlightTheme.colors.border)
                .clickable {
                    onDecreaseClick(count)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.decrease),
                contentDescription = "Minus",
                tint = MoonlightTheme.colors.highlightText,
            )
        }
        TextFieldTextWidget(
            modifier = Modifier
                .width(18.dp),
            text = count.toString(),
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(shape = MoonlightTheme.shapes.checkBoxShape)
                .background(MoonlightTheme.colors.border)
                .clickable {
                    onIncreaseClick(count)
                },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(R.drawable.increase),
                contentDescription = "Plus",
                tint = MoonlightTheme.colors.highlightText,
            )
        }
    }
}

@Composable
private fun DeleteButton(
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .clip(shape = MoonlightTheme.shapes.checkBoxShape)
            .clickable { onDeleteClick() },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.delete),
            contentDescription = "Delete",
            tint = MoonlightTheme.colors.highlightText,
        )
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