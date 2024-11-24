package com.example.mobileappproject.data.repositories


import com.example.mobileappproject.data.entities.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getOrderStream(userId: Int): Flow<List<Order?>>
    fun getSpecificOrderStream(id: Int, serviceId : Int): Flow<Order?>
    suspend fun insertOrder(order: Order)
    suspend fun deleteOrder(order: Order)
    suspend fun updateOrder(order: Order)
    suspend fun fetchOrdersFromApi(userId: Int): List<Order>
}