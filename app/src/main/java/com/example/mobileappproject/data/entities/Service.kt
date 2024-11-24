package com.example.mobileappproject.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "service")
data class Service(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Auto-generates the ID
    val subcategory: String,
    val serviceName: String,
    val description: String,
    val price: Int,
    val image: String? = null,         // Nullable if no image is provided
    val createdAt: Date,
    val updatedAt: Date
)

data class ServiceResponse(
    val message: String,
    val services: List<ServiceModel>
)

data class ServiceModel(
    val _id: String,
    val subcategory: String,
    val serviceName: String,
    val description: String,
    val price: Int,
    val createdAt: String, // API returns dates as strings
    val updatedAt: String
)
