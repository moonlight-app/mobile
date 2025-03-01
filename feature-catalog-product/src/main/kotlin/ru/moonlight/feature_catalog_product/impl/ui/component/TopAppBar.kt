package ru.moonlight.feature_catalog_product.impl.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.component.TopAppBarComponent
import ru.moonlight.feature_catalog_product.R

@Composable
internal fun TopAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBarComponent(
        modifier = modifier,
        title = stringResource(R.string.review),
        onBackClick = onBackClick
    )
}