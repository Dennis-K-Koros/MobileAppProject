package com.example.mobileappproject.data.api


import com.example.mobileappproject.data.entities.Order
import com.example.mobileappproject.data.entities.OrderModel
import com.example.mobileappproject.data.entities.OrderResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderApi {
    @GET("/order/user/{userId}")
    suspend fun getOrdersByUserId(@Path("userId") userId: String): Response<OrderResponse>

    @GET("/order/{orderId}")
    suspend fun getOrderById(@Path("orderId") orderId: String): Response<Order>

    @POST("/order/create")
    suspend fun createOrder(@Body order: Order): Response<Order>

    @PUT("/order/update/{orderId}")
    suspend fun updateOrder(@Path("orderId") orderId: String, @Body order: Order): Response<Order>

    @DELETE("order/delete/{orderId}")
    suspend fun deleteOrder(@Path("orderId") orderId: String): Response<Void>
}

