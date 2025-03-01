package ru.moonlight.database.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "local_cart",
)
data class LocalCartEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("item_id") val itemId: Long,
    @ColumnInfo("product_id") val productId: Long,
    @ColumnInfo("created_at") val createdAt: String,
    val count: Int,
    @ColumnInfo("is_favorite") val isFavorite: Boolean,
    val chosen: Boolean,
)