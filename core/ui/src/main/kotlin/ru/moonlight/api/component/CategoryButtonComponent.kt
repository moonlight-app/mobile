package ru.moonlight.api.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import ru.moonlight.api.animation.bouncingClickable
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.text.SubTitleTextWidget

@Composable
fun CategoryButtonComponent(
    onCategoryClick: (String) -> Unit,
    title: String,
    productType: String,
    @DrawableRes image: Int,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .bouncingClickable {
                onCategoryClick(productType)
            },
        shape = MoonlightTheme.shapes.buttonShape,
        border = BorderStroke(1.dp, color = MoonlightTheme.colors.border),
        colors = CardDefaults.cardColors(
            containerColor = MoonlightTheme.colors.card2,
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            SubTitleTextWidget(
                modifier = Modifier
                    .padding(MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
                text = title,
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Inside,
                )
            }
        }
    }
}