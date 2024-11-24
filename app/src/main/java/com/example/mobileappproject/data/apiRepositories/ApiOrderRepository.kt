package com.example.mobileappproject.data.apiRepositories

import com.example.mobileappproject.data.api.OrderApi
import com.example.mobileappproject.data.dao.OrderDao
import com.example.mobileappproject.data.entities.Order
import com.example.mobileappproject.data.entities.OrderModel
import com.example.mobileappproject.data.entities.OrderResponse
import com.example.mobileappproject.data.repositories.OrderRepository
import kotlinx.coroutines.flow.Flow

class ApiOrderRepository(
    private val orderDao: OrderDao,
    private val api: OrderApi
) : OrderRepository {

    // Fetch all orders for a user from the local database
    override fun getOrderStream(userId: String): Flow<List<Order>> =
        orderDao.getOrders(userId)

    // Fetch a specific order for a user and service from the local database
    override fun getSpecificOrderStream(userId: String, serviceId: String): Flow<Order?> =
        orderDao.getSpecificOrder(userId, serviceId)

    // Insert a single order into the database
    override suspend fun insertOrder(order: Order) = orderDao.insert(order)

    // Delete a specific order from the database
    override suspend fun deleteOrder(order: Order) = orderDao.delete(order)

    // Update a specific order in the database
    override suspend fun updateOrder(order: Order) = orderDao.update(order)

    // Fetch orders from the API and save them to the local database
    override suspend fun fetchOrdersFromApi(userId: String): List<Order> {
        return try {
            val response = api.getOrdersByUserId(userId)
            if (response.isSuccessful) {
                val apiResponse = response.body()
                val orderModels = apiResponse?.orders
                if (orderModels != null) {
                    val orderEntities = orderModels.map { mapToOrderEntity(it) }
                    orderDao.insertAll(orderEntities)
                    orderEntities
                } else {
                    println("No orders found in API response")
                    emptyList()
                }
            } else {
                println("API error: ${response.message()}")
                emptyList()
            }
        } catch (e: Exception) {
            println("Exception in fetchOrdersFromApi: ${e.message}")
            emptyList()
        }
    }


    // Mapper function to convert API model to Room entity
    private fun mapToOrderEntity(orderModel: OrderModel): Order {
        return Order(
            id = orderModel._id,
            userId = orderModel.customer._id,
            technician = orderModel.technician._id,
            service = orderModel.service,
            status = orderModel.status,
            orderDate = orderModel.orderDate,
            completionDate = null, // Replace with actual mapping if available
            createdAt = orderModel.createdAt,
            updatedAt = orderModel.updatedAt
        )
    }
}
