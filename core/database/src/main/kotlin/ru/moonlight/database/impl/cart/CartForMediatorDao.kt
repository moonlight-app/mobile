package ru.moonlight.database.impl.cart

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CartForMediatorDao {

    @Query("SELECT * FROM mediator_cart ORDER BY created_at DESC")
    fun getAllItems(): Flow<List<CartForMediatorEntity>>

    @Query("SELECT item_id FROM mediator_cart")
    suspend fun getAllItemIds(): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: CartForMediatorEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<CartForMediatorEntity>)

    @Query("SELECT COUNT(*) FROM mediator_cart")
    suspend fun getCountOfRows(): Int

    @Query("DELETE FROM mediator_cart WHERE item_id NOT IN (:cartItemIds)")
    suspend fun deleteOldItems(cartItemIds: List<Long>)

    @Query("DELETE FROM mediator_cart")
    suspend fun deleteAll()

    @Update
    suspend fun updateItem(cartEntity: CartForMediatorEntity)

    @Query("DELETE FROM mediator_cart")
    suspend fun clearAll()

    @Query("DELETE FROM mediator_cart WHERE item_id = :id")
    suspend fun deleteItem(id: Long)

    @Query("SELECT EXISTS(SELECT 1 FROM mediator_cart WHERE item_id = :id)")
    suspend fun isItemExists(id: Long): Boolean

    @Query("SELECT * FROM mediator_cart ORDER BY created_at DESC")
    fun cartPagingSource(): PagingSource<Int, CartForMediatorEntity>

}