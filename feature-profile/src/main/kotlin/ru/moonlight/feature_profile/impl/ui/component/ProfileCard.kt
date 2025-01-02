package ru.moonlight.feature_profile.impl.ui.component

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.moonlight.api.theme.MoonlightTheme
import ru.moonlight.api.widget.text.ButtonTextWidget
import ru.moonlight.api.widget.text.SecondTitleTextWidget

@Composable
internal fun ProfileCard(
    name: String,
    email: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = MoonlightTheme.dimens.paddingFromEdges),
        horizontalArrangement = Arrangement.spacedBy(
            space = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal,
            alignment = Alignment.Start,
        ),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(68.dp)
                .clip(CircleShape)
                .background(color = MoonlightTheme.colors.highlightComponent)
        )
        Column(
            modifier = Modifier
                .height(68.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            SecondTitleTextWidget(text = name)
            ButtonTextWidget(text = email)
        }
    }
}