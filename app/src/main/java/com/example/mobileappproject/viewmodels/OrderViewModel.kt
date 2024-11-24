package com.example.mobileappproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileappproject.data.entities.Order
import com.example.mobileappproject.data.repositories.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderViewModel(private val repository: OrderRepository) : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> get() = _orders

    private val _selectedOrder = MutableStateFlow<Order?>(null)
    val selectedOrder: StateFlow<Order?> get() = _selectedOrder

    fun fetchOrders(userId: String) { // Updated to String
        viewModelScope.launch {
            val fetchedOrders = repository.fetchOrdersFromApi(userId)
            _orders.value = fetchedOrders
        }
    }


    fun insertOrder(order: Order) {
        viewModelScope.launch {
            repository.insertOrder(order)
            fetchOrders(order.userId) // Refresh orders after insertion
        }
    }

    fun deleteOrder(order: Order) {
        viewModelScope.launch {
            repository.deleteOrder(order)
            fetchOrders(order.userId) // Refresh orders after deletion
        }
    }

    fun selectOrder(order: Order) {
        _selectedOrder.value = order
    }
}
