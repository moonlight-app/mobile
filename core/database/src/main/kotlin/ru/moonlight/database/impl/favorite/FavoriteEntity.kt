package ru.moonlight.database.impl.favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite",
)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "product_id")
    val productId: Long,
    val name: String,
    @ColumnInfo(name = "preview_url") val previewUrl: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean = true,
    @ColumnInfo(name = "created_at") val createdAt: String,
    val price: Double,
    val type: String,
)