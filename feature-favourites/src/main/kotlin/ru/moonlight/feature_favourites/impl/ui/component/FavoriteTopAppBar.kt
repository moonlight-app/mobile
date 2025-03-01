package ru.moonlight.feature_favourites.impl.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.component.TopAppBarComponent
import ru.moonlight.feature_favourites.R

@Composable
internal fun FavoriteTopAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBarComponent(
        modifier = modifier,
        onBackClick = onBackClick,
        title = stringResource(R.string.favorite),
    )
}