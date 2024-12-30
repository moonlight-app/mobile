package ru.moonlight.feature_catalog_sort.impl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.moonlight.theme.MoonlightTheme

@Composable
internal fun SortButton(
    onSortSelected: () -> Unit,
    text: String,
    icon: Int,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSortSelected() },
    ) {
        Border()
        IconText(text, icon, isSelected)
    }
}

@Composable
private fun Border(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier
            .fillMaxWidth()
            .height(0.5.dp)
            .background(color = Color.White),
    )
}

@Composable
private fun IconText(
    text: String,
    icon: Int,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = MoonlightTheme.dimens.paddingBetweenComponentsHorizontal),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            MoonlightTheme.dimens.paddingBetweenComponentsHorizontal
        ),
    ) {
        Icon(
            modifier = Modifier
                .width(MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical)
                .height(MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical + 2.dp),
            painter = painterResource(icon),
            contentDescription = "$text icon",
            tint = if (isSelected) MoonlightTheme.colors.text
            else MoonlightTheme.colors.hintText
        )
        Text(
            modifier = Modifier
                .padding(
                    top = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical - 1.dp,
                    bottom = MoonlightTheme.dimens.paddingBetweenComponentsSmallVertical
                ),
            text = text,
            color = if (isSelected) MoonlightTheme.colors.text
            else MoonlightTheme.colors.hintText,
            style = MoonlightTheme.typography.button,
        )
    }
}