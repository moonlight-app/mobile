package ru.moonlight.database.catalog

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "products",
)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo("product_id") val productId: Long,
    val name: String,
    val previewUrl: String,
    val price: Double,
    @ColumnInfo("is_favorite") val isFavorite: Boolean = false,
)