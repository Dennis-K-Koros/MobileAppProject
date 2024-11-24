package com.example.mobileappproject.data.api


import com.example.mobileappproject.data.entities.Order
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderApi {
    @GET("order/user/{userId}/order/{orderId}") // Adjust to your endpoint
    suspend fun getSpecificOrder(@Path("id") id: String ): Response<Order>

    @GET("order/user/{id}")
    suspend fun getUserOrders(@Path("id") id: String): Response<List<Order>>

    @GET("order/{id}")
    suspend fun getOrderById(@Path("id") id: String): Response<Order>

    @POST("order/create")
    suspend fun createOrder(@Body order: Order): Response<Order>

    @PUT("order/update/{id}")
    suspend fun updateOrder(@Path("id") id: String, @Body order: Order): Response<Order>

    @DELETE("order/delete/{id}")
    suspend fun deleteOrder(@Path("id") id: String): Response<Void>
}