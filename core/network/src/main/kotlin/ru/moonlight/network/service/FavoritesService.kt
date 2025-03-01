package ru.moonlight.network.service

import ru.moonlight.common.ApiResponse
import ru.moonlight.network.api.FavoritesApi
import ru.moonlight.network.model.favorite.FavoriteResponse
import ru.moonlight.network.utils.ApiCallController
import javax.inject.Inject

interface FavoritesService {
    suspend fun addToFavorites(id: Long): ApiResponse<Unit>
    suspend fun removeFromFavorites(id: Long): ApiResponse<Unit>
    suspend fun getFavorites(page: Int): ApiResponse<FavoriteResponse>
}

internal class FavoritesApiService @Inject constructor(
    private val api: FavoritesApi,
    private val apiCallController: ApiCallController,
): FavoritesService {
    override suspend fun addToFavorites(id: Long): ApiResponse<Unit> =
        apiCallController.safeApiCall {
            api.addToFavorites(id = id.toString())
        }

    override suspend fun removeFromFavorites(id: Long): ApiResponse<Unit> =
        apiCallController.safeApiCall {
            api.removeFromFavorites(id = id.toString())
        }

    override suspend fun getFavorites(page: Int): ApiResponse<FavoriteResponse> =
        apiCallController.safeApiCall {
            api.getFavorites(page)
        }

}