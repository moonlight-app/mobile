package ru.moonlight.database.impl.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "mediator_cart",
    indices = [
        Index(value = ["item_id"], unique = true),
    ]
)
data class CartForMediatorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "item_id") val itemId: Long,
    val count: Int,
    @ColumnInfo("created_at") val createdAt: String,
    @ColumnInfo("is_favorite") val isFavorite: Boolean,
    val name: String,
    @ColumnInfo("preview_url") val previewUrl: String,
    val price: Double,
    @ColumnInfo("product_id") val productId: Long,
    val size: String?,
    val type: String,
)