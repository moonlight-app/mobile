package ru.moonlight.api.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

object MoonlightIcons {

    public val Icons.Outlined.Catalog: ImageVector
        get() {
            val _catalog = materialIcon(name = "Outlined.Catalog") {
                materialPath {
                    moveTo(12.0f, 5.69f)
                    lineToRelative(5.0f, 4.5f)
                    verticalLineTo(18.0f)
                    horizontalLineToRelative(-2.0f)
                    verticalLineToRelative(-6.0f)
                    horizontalLineTo(9.0f)
                    verticalLineToRelative(6.0f)
                    horizontalLineTo(7.0f)
                    verticalLineToRelative(-7.81f)
                    lineToRelative(5.0f, -4.5f)
                    moveTo(12.0f, 3.0f)
                    lineTo(2.0f, 12.0f)
                    horizontalLineToRelative(3.0f)
                    verticalLineToRelative(8.0f)
                    horizontalLineToRelative(6.0f)
                    verticalLineToRelative(-6.0f)
                    horizontalLineToRelative(2.0f)
                    verticalLineToRelative(6.0f)
                    horizontalLineToRelative(6.0f)
                    verticalLineToRelative(-8.0f)
                    horizontalLineToRelative(3.0f)
                    lineTo(12.0f, 3.0f)
                    close()
                }
            }
            return _catalog
        }

}
