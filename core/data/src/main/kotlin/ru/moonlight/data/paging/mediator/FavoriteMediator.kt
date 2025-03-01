package ru.moonlight.data.paging.mediator

import android.net.http.HttpException
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import ru.moonlight.common.ApiResponse
import ru.moonlight.data.mapper.mapToEntity
import ru.moonlight.database.MoonlightDatabase
import ru.moonlight.database.favorite.FavoriteEntity
import ru.moonlight.network.model.favorite.FavoriteProducts
import ru.moonlight.network.service.FavoritesService
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class FavoriteMediator(
    private val service: FavoritesService,
    private val database: MoonlightDatabase,
): RemoteMediator<Int, FavoriteEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FavoriteEntity>
    ): MediatorResult {
        return try {
            val currentPage: Int = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null) { 1 }
                    else { (database.favoriteDao.getCountOfRows() / state.config.pageSize) + 1 }
                }
            }

            val response = service.getFavorites(page = currentPage)

            when (response) {
                is ApiResponse.Error -> MediatorResult.Error(Exception(response.msg))
                is ApiResponse.Success -> {
                    if(loadType == LoadType.REFRESH) {
                        database.favoriteDao.clearAll()
                    }
                    val favoriteProducts = response.data?.favoriteProducts ?: emptyList()

                    if (favoriteProducts.isEmpty()) {
                        database.favoriteDao.clearAll()
                    } else {
                        database.favoriteDao.upsertAll(favoriteProducts.map { it.mapToEntity() })
                    }

                    MediatorResult.Success(
                        endOfPaginationReached = (response.data?.productPage?.totalPages ?: 1) == currentPage
                    )
                }
            }
        } catch(e: IOException) {
            MediatorResult.Error(e)
        } catch(e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}