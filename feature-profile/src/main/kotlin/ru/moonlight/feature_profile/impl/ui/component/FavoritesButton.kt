package ru.moonlight.feature_profile.impl.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.component.ProfileButtonComponent
import ru.moonlight.feature_profile.R

@Composable
internal fun FavoritesButton(
    onFavoritesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ProfileButtonComponent(
        modifier = modifier,
        text = stringResource(R.string.favorites),
        onClick = onFavoritesClick,
    )
}