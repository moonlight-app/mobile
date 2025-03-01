package ru.moonlight.api.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
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
import ru.moonlight.api.widget.text.DescriptionTextWidget
import ru.moonlight.api.widget.text.SubTitleTextWidget
import ru.moonlight.ui.R

@Composable
fun OrderItem(
    imageUrl: String,
    title: String,
    status: String,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MoonlightTheme.shapes.buttonShape,
        border = BorderStroke(
            1.dp,
            color = if (status == stringResource(R.string.in_delivery)) MoonlightTheme.colors.highlightComponent
            else MoonlightTheme.colors.border
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = MoonlightTheme.colors.text,
        )
    ) {
        Row(
            modifier = Modifier
                .padding(MoonlightTheme.dimens.paddingBetweenComponentsHorizontal)
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(
                space = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal,
                alignment = Alignment.Start,
            ),
            verticalAlignment = Alignment.Top
        ) {
            ProductImage(imageUrl = imageUrl)
            Column(
                modifier = Modifier
                    .height(42.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                SubTitleTextWidget(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = title,
                    maxLines = 1,
                )
                DescriptionTextWidget(
                    text = status,
                    textColor = if (status == stringResource(R.string.in_delivery)) MoonlightTheme.colors.highlightComponent
                    else MoonlightTheme.colors.disabledComponent,
                )
            }
        }
    }
}

@Composable
private fun ProductImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    SubcomposeAsyncImage(
        modifier = modifier
            .size(52.dp, 42.dp)
            .clip(MoonlightTheme.shapes.buttonShape)
            .background(color = MoonlightTheme.colors.text),
        model = imageUrl,
        loading = {
            Box(
                modifier = Modifier
                    .padding(MoonlightTheme.dimens.paddingFromEdges)
                    .size(52.dp, 42.dp)
                    .clip(MoonlightTheme.shapes.buttonShape)
                    .background(color = Color.Gray),
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
                    .size(52.dp, 42.dp)
                    .clip(MoonlightTheme.shapes.buttonShape)
                    .background(color = Color.Gray),
                contentScale = ContentScale.Inside,
            )
        },
        contentScale = ContentScale.Crop,
        contentDescription = "Product preview",
    )
}