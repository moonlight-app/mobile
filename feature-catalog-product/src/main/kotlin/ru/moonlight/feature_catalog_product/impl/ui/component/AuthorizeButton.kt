package ru.moonlight.feature_catalog_product.impl.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.moonlight.api.widget.button.ButtonWidget
import ru.moonlight.feature_catalog_product.R

@Composable
internal fun AuthorizeButton(
    onAuthClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ButtonWidget(
        modifier = modifier,
        onClick = onAuthClick,
        text = stringResource(R.string.signIn),
    )
}