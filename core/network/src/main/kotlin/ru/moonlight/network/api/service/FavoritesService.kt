package ru.moonlight.network.api.service

import ru.moonlight.common.ApiResponse
import ru.moonlight.network.api.model.favorite.FavoriteResponse

interface FavoritesService {
    suspend fun addToFavorites(id: Long): ApiResponse<Unit>
    suspend fun removeFromFavorites(id: Long): ApiResponse<Unit>
    suspend fun getFavorites(page: Int): ApiResponse<FavoriteResponse>
}