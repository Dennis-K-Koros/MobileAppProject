package com.example.mobileappproject.data.apiRepositories

import com.example.mobileappproject.data.api.OrderApi
import com.example.mobileappproject.data.dao.OrderDao
import com.example.mobileappproject.data.entities.Order
import com.example.mobileappproject.data.repositories.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class ApiOrderRepository (
    private val orderDao: OrderDao,
    private val api: OrderApi
) : OrderRepository {
    override fun getOrderStream(userId: String): Flow<List<Order?>> = orderDao.getOrders(userId)
    override fun getSpecificOrderStream(userId: String, serviceId : String): Flow<Order?> = orderDao.getSpecificOrder(userId,serviceId)
    override suspend fun insertOrder(order: Order) = orderDao.insert(order)
    override suspend fun deleteOrder(order: Order) = orderDao.delete(order)
    override suspend fun updateOrder(order: Order) = orderDao.update(order)
    override suspend fun fetchOrdersFromApi(userId: String): List<Order> {
        return try {
            val response = api.getUserOrders(userId)
            if (response.isSuccessful) {
                response.body()?.let { orders ->
                    orders.forEach { orderDao.insert(it) } // Cache fetched orders
                    return orders
                }
            }
            orderDao.getOrders(userId).firstOrNull() ?: emptyList() // Fallback to local cache
        } catch (e: Exception) {
            println("Error fetching orders: ${e.message}")
            orderDao.getOrders(userId).firstOrNull() ?: emptyList()
        }
    }


}