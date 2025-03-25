package ru.moonlight.data.api.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.moonlight.common.ApiResponse
import ru.moonlight.domain_model.catalog.CatalogProductDomainModel

interface FavoritesRepository {
    suspend fun changeFavouriteStatusByIdTo(id: Long, status: Boolean): ApiResponse<Unit>
    fun getFavorites(): Flow<PagingData<CatalogProductDomainModel>>
}