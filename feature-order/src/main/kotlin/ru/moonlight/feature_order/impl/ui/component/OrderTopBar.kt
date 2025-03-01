package ru.moonlight.feature_order.impl.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.moonlight.api.component.TopAppBarComponent

@Composable
fun OrderTopBar(
    onBackClick:() -> Unit,
    title: String,
    modifier: Modifier = Modifier,
) {
    TopAppBarComponent(
        onBackClick = onBackClick,
        title = title,
        modifier = modifier
    )
}