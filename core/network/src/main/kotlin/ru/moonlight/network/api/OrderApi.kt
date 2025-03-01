package ru.moonlight.network.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.moonlight.network.model.order.OrderResponse

internal interface OrderApi {

    @GET("/api/orders")
    suspend fun getOrders(
        @Query("page") page: Int,
    ): Response<OrderResponse>

    @POST("/api/orders")
    suspend fun createOrder(
        @Query("cart_items") itemIds: List<Long>,
    ): Response<Unit>

}