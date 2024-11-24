package com.example.mobileappproject.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "Order")
data class Order(
    @PrimaryKey val id: String, // No auto-generation since the API provides `_id`
    val userId: String,
    val technician: String,
    val service: String,
    val status: String,
    val orderDate: String,
    val completionDate: Date?, // Nullable, assuming not always available
    val createdAt: String,
    val updatedAt: String
)

data class OrderResponse(
    val status: String, // API response status
    val orders: List<OrderModel>? // Ensure this matches the API's structure
)

data class OrderModel(
    val _id: String,
    val customer: Customer,
    val technician: Technician,
    val service: String,
    val status: String,
    val orderDate: String,
    val createdAt: String,
    val updatedAt: String,
)

data class Customer(
    val _id: String,
    val username: String,
    val email: String
)

data class Technician(
    val _id: String,
    val username: String,
    val email: String
)


