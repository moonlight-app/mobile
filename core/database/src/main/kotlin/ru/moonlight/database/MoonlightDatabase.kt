package ru.moonlight.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.moonlight.database.cart.CartForMediatorDao
import ru.moonlight.database.cart.CartForMediatorEntity
import ru.moonlight.database.cart.LocalCartDao
import ru.moonlight.database.cart.LocalCartEntity
import ru.moonlight.database.catalog.ProductDao
import ru.moonlight.database.catalog.ProductEntity
import ru.moonlight.database.favorite.FavoriteDao
import ru.moonlight.database.favorite.FavoriteEntity

@Database(
    entities = [
        ProductEntity::class,
        CartForMediatorEntity::class,
        LocalCartEntity::class,
        FavoriteEntity::class,
    ],
    version = 1
)

abstract class MoonlightDatabase: RoomDatabase() {
    abstract val productDao: ProductDao
    abstract val mediatorCartDao: CartForMediatorDao
    abstract val localCartDao: LocalCartDao
    abstract val favoriteDao: FavoriteDao
}