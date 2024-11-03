package ru.moonlight.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.moonlight.theme.MoonlightTheme

@Composable
fun RowScope.NavigationBarItemComponent(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    selectedIconColor: Color = MoonlightTheme.colors.text,
    unselectedIconColor: Color = MoonlightTheme.colors.hintText,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = selectedIconColor,
            unselectedIconColor = unselectedIconColor,
            indicatorColor = Color.Transparent,
        ),
    )
}

@Composable
fun BottomNavigationComponent(
    modifier: Modifier = Modifier,
    containerColor: Color = MoonlightTheme.colors.card,
    contentColor: Color = MoonlightTheme.colors.text,
    content: @Composable RowScope.() -> Unit
) {
    NavigationBar(
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
    ) {
        content()
    }
}