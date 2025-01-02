package ru.moonlight.api.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import ru.moonlight.api.animation.bouncingClickable
import ru.moonlight.api.theme.MoonlightTheme

@Composable
fun ProfileButtonComponent(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal)
            .clip(shape = MoonlightTheme.shapes.buttonShape)
            .bouncingClickable { onClick() },
        shape = MoonlightTheme.shapes.buttonShape,
        colors = CardDefaults.cardColors(
            containerColor = MoonlightTheme.colors.card2,
            contentColor = MoonlightTheme.colors.text,
        )
    ) {
        Box(
            modifier = Modifier
                .padding(MoonlightTheme.dimens.paddingBetweenComponentsHorizontal * 2),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text = text,
                style = MoonlightTheme.typography.subTitle,
            )
        }
    }
}