package ru.moonlight.database.impl.favorite

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(products: List<FavoriteEntity>)

    @Query("SELECT * FROM favorite")
    fun productPagingSource(): PagingSource<Int, FavoriteEntity>

    @Query("DELETE FROM favorite WHERE product_id = :id")
    suspend fun deleteProductById(id: Long)

    @Query("DELETE FROM favorite")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM favorite")
    suspend fun getCountOfRows(): Int

}