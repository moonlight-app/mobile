package ru.moonlight.network.impl.api

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query
import ru.moonlight.network.api.model.cart.CartItemsResponse


internal interface CartApi {

    @GET("/api/cart")
    suspend fun getCart(
        @Query("page") page: Int,
    ): Response<CartItemsResponse>

    @POST("/api/cart")
    suspend fun addItemToCart(
        @Query("product_id") id: String,
        @Query("size") size: String?,
        @Query("count") count: String,
    ): Response<Unit>

    @PATCH("/api/cart")
    suspend fun changeCount(
        @Query("item_id") id: Long,
        @Query("count") count: Int
    ): Response<Unit>

    @DELETE("/api/cart")
    suspend fun removeItemFromCart( @Query("item_id") id: Long): Response<Unit>

}