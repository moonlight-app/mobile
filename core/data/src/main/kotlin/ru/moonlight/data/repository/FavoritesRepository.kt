package ru.moonlight.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.moonlight.common.ApiResponse
import ru.moonlight.common.di.Dispatcher
import ru.moonlight.common.di.MoonlightDispatchers
import ru.moonlight.data.mapper.mapToDomain
import ru.moonlight.data.paging.mediator.FavoriteMediator
import ru.moonlight.database.MoonlightDatabase
import ru.moonlight.database.favorite.FavoriteEntity
import ru.moonlight.domain_model.catalog.CatalogProductDomainModel
import ru.moonlight.network.service.FavoritesService
import javax.inject.Inject

interface FavoritesRepository {
    suspend fun changeFavouriteStatusByIdTo(id: Long, status: Boolean): ApiResponse<Unit>
    fun getFavorites(): Flow<PagingData<CatalogProductDomainModel>>
}

internal class FavoritesRepositoryImpl @Inject constructor(
    private val service: FavoritesService,
    private val database: MoonlightDatabase,
    @Dispatcher(MoonlightDispatchers.IO) private val dispatcherIO: CoroutineDispatcher,
) : FavoritesRepository {
    override suspend fun changeFavouriteStatusByIdTo(id: Long, status: Boolean): ApiResponse<Unit> = withContext(dispatcherIO) {
        val response = if (status)
            service.addToFavorites(id)
        else
            service.removeFromFavorites(id)

        if (response is ApiResponse.Success) {
            changeStatusInsideDatabases(id, status)
            if (!status) database.favoriteDao.deleteProductById(id)
        }

        return@withContext response
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getFavorites(): Flow<PagingData<CatalogProductDomainModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = FavoriteMediator(service = service, database = database),
            pagingSourceFactory = {
                database.favoriteDao.productPagingSource()
            }
        ).flow
            .flowOn(dispatcherIO)
            .map { data: PagingData<FavoriteEntity> ->
                data.map { product -> product.mapToDomain() }
            }
    }

    private suspend fun changeStatusInsideDatabases(id: Long, status: Boolean) {
        database.productDao.updateFavoriteStatus(id, status)
        database.localCartDao.updateFavouriteStatus(id, status)
    }

}