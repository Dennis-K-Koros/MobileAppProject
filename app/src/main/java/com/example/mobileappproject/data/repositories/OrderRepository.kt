package com.example.mobileappproject.data.repositories

import com.example.mobileappproject.data.entities.Order
import com.example.mobileappproject.data.entities.OrderResponse
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getOrderStream(userId: String): Flow<List<Order>>
    fun getSpecificOrderStream(userId: String, serviceId: String): Flow<Order?>
    suspend fun insertOrder(order: Order)
    suspend fun deleteOrder(order: Order)
    suspend fun updateOrder(order: Order)
    suspend fun fetchOrdersFromApi(userId: String): List<Order>
}
