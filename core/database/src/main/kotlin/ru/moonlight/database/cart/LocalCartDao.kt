package ru.moonlight.database.cart

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalCartDao {

    @Query("SELECT * FROM local_cart ORDER BY created_at DESC")
    fun getAllItems(): Flow<List<LocalCartEntity>>

    @Query("SELECT item_id FROM local_cart")
    suspend fun getAllItemIds(): List<Long>

    @Query("SELECT item_id FROM local_cart WHERE chosen = 1")
    suspend fun getAllAvailableItemIds(): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: LocalCartEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(products: List<LocalCartEntity>)

    @Query("DELETE FROM local_cart WHERE item_id NOT IN (:cartItemIds)")
    suspend fun deleteOldItems(cartItemIds: List<Long>)

    @Query("DELETE FROM local_cart")
    suspend fun deleteAll()

    @Transaction
    suspend fun insertNewItemsAndDeleteOldItems(products: List<LocalCartEntity>) {
        val cartItemIds = products.map { it.itemId }
        insertAll(products)
        deleteOldItems(cartItemIds)
    }

    @Update
    suspend fun updateItem(cartEntity: LocalCartEntity)

    @Query("UPDATE local_cart SET chosen = :chosen WHERE item_id = :id")
    suspend fun updateChosenItemStatus(id: Long, chosen: Boolean)

    @Query("UPDATE local_cart SET chosen = :chosen")
    suspend fun updateChosenItemStatusForAllItems(chosen: Boolean)

    @Query("UPDATE local_cart SET count = :count WHERE item_id = :id")
    suspend fun updateItemCount(id: Long, count: Int)

    @Query("UPDATE local_cart SET is_favorite = :status WHERE product_id = :id")
    suspend fun updateFavouriteStatus(id: Long, status: Boolean)

    @Query("DELETE FROM local_cart")
    suspend fun clearAll()

    @Query("DELETE FROM local_cart WHERE item_id = :id")
    suspend fun deleteItem(id: Long)

    @Query("SELECT chosen FROM local_cart WHERE item_id = :id")
    suspend fun isItemAvailable(id: Long): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM local_cart WHERE item_id = :id)")
    suspend fun isItemExists(id: Long): Boolean

    @Query("SELECT * FROM local_cart ORDER BY created_at DESC")
    fun cartPagingSource(): PagingSource<Int, LocalCartEntity>

}