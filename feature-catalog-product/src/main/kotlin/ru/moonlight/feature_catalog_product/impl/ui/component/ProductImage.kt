package ru.moonlight.feature_catalog_product.impl.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import coil3.compose.SubcomposeAsyncImage
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.progressbar.ProgressBarWidget

@Composable
internal fun ProductImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier,
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
                    bitmap = ImageBitmap.imageResource(ru.moonlight.ui.R.drawable.ring),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Inside,
                )
            },
            contentScale = ContentScale.Inside,
            contentDescription = "Product preview",
        )
    }
}

