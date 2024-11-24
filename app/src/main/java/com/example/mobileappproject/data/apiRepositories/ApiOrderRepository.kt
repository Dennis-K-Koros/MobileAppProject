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
    override fun getOrderStream(userId: Int): Flow<List<Order?>> = orderDao.getOrders(userId)
    override fun getSpecificOrderStream(id: Int, serviceId : Int): Flow<Order?> = orderDao.getSpecificOrder(id,serviceId)
    override suspend fun insertOrder(order: Order) = orderDao.insert(order)
    override suspend fun deleteOrder(order: Order) = orderDao.delete(order)
    override suspend fun updateOrder(order: Order) = orderDao.update(order)
    override suspend fun fetchOrdersFromApi(userId: Int): List<Order> {
        return try {
            val response = api.getUserOrders(userId.toString())
            if (response.isSuccessful) {
                response.body()?.let { orders ->
                    orders.forEach { orderDao.insert(it) }
                    return orders
                }
            }
            orderDao.getOrders(userId).firstOrNull() ?: emptyList() // Fallback to cached data
        } catch (e: Exception) {
            println("Error fetching orders: ${e.message}")
            orderDao.getOrders(userId).firstOrNull() ?: emptyList()
        }
    }

}