package ru.moonlight.api.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import ru.moonlight.api.theme.MoonlightTheme

@Composable
fun RowScope.NavigationBarItemComponent(
    onClick: () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
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
        enabled = enable,
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

@Composable
fun BadgedBoxComponent(
    badgeCount: Int?,
    selected: Boolean,
    icon: ImageVector,
) {
    BadgedBox(
        badge = {
            if(badgeCount != null) {
                Badge(
                    containerColor = MoonlightTheme.colors.highlightComponent,
                    contentColor = MoonlightTheme.colors.text,
                ) {
                    Text(text = badgeCount.toString())
                }
            }
        }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (selected) MoonlightTheme.colors.text else MoonlightTheme.colors.hintText
        )
    }
}