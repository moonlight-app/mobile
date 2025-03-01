package ru.moonlight.api.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.progressbar.ProgressBarWidget
import ru.moonlight.api.widget.text.SubTitleTextWidget
import ru.moonlight.api.widget.text.TextFieldTextWidget
import ru.moonlight.ui.R

data class OrderUiModel(
    val id: Long,
    val title: String,
    val type: String,
    val imageUrl: String,
    val price: String,
    val size: String?,
    val status: String,
)

fun String.convertStatusToUiModel(context: Context): String {
    return when (this) {
        "in_delivery" -> context.getString(R.string.in_delivery)
        "closed" -> context.getString(R.string.closed)
        else -> "Unknown status"
    }
}

@Composable
fun OrderFeedItemComponent(
    onProductClick: (Long) -> Unit,
    id: Long,
    title: String,
    type: String,
    imageUrl: String,
    price: String,
    size: String?,
    status: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = MoonlightTheme.shapes.buttonShape)
            .background(color = MoonlightTheme.colors.card2)
            .border(
                width = 1.dp,
                color = if (status == stringResource(R.string.in_delivery)) MoonlightTheme.colors.highlightComponent
                        else Color.Transparent,
                shape = MoonlightTheme.shapes.buttonShape
            )
            .padding(MoonlightTheme.dimens.paddingBetweenComponentsHorizontal)
            .clickable { onProductClick(id) },
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(
            space = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal,
            alignment = Alignment.Start,
        ),
    ) {
        ProductImageWithFavoriteButton(
            modifier = Modifier,
            imageUrl = imageUrl,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(132.dp),
            verticalArrangement = Arrangement.spacedBy(
                MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical/2,
            )
        ) {
            SubTitleTextWidget(text = "${price.dropLast(2)} ₽", maxLines = 3)
            TextFieldTextWidget(text = title, maxLines = 2)
            if (size != null) TextFieldTextWidget(text = "$size ${stringResource(R.string.size)}")
            TextFieldTextWidget(
                text = status,
                textColor = if (status == stringResource(R.string.in_delivery)) MoonlightTheme.colors.highlightComponent
                            else MoonlightTheme.colors.hintText
            )


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
            .fillMaxWidth(0.45f)
            .height(132.dp)
            .background(Color.White),
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
        contentScale = ContentScale.FillHeight,
        contentDescription = "Product preview",
    )
}
