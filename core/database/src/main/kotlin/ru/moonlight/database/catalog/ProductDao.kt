package ru.moonlight.database.catalog

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(products: List<ProductEntity>)

    @Query("SELECT * FROM products")
    fun productPagingSource(): PagingSource<Int, ProductEntity>

    @Query("DELETE FROM products")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM products")
    suspend fun getCountOfRows(): Int

    @Query("UPDATE products SET is_favorite = :status WHERE product_id = :id")
    suspend fun updateFavoriteStatus(id: Long, status: Boolean)

}