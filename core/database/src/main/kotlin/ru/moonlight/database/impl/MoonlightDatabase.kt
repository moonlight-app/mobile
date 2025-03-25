package ru.moonlight.database.impl

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.moonlight.database.impl.cart.CartForMediatorDao
import ru.moonlight.database.impl.cart.CartForMediatorEntity
import ru.moonlight.database.impl.cart.LocalCartDao
import ru.moonlight.database.impl.cart.LocalCartEntity
import ru.moonlight.database.impl.catalog.ProductDao
import ru.moonlight.database.impl.catalog.ProductEntity
import ru.moonlight.database.impl.favorite.FavoriteDao
import ru.moonlight.database.impl.favorite.FavoriteEntity

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