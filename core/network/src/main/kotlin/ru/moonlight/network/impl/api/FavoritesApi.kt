package ru.moonlight.network.impl.api

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.moonlight.network.api.model.favorite.FavoriteResponse

internal interface FavoritesApi {

    @POST("/api/favorites")
    suspend fun addToFavorites(@Query("product_id") id: String): Response<Unit>

    @DELETE("/api/favorites")
    suspend fun removeFromFavorites(@Query("product_id") id: String): Response<Unit>

    @GET("/api/favorites")
    suspend fun getFavorites(@Query("page") page: Int): Response<FavoriteResponse>

}