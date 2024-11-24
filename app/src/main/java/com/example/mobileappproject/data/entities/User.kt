package com.example.mobileappproject.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey val id: String, // Provided by the backend
    val username: String,
    val email: String,
    val phone: String,
    val password: String,
    val role: String = "customer",
    val isTechnician: Boolean = false, // Defaults to false
    val image: String? = null,         // Nullable if no image is provided
    val verified: Boolean = false,     // Changed to Boolean for clarity
    val createdAt: String,
    val updatedAt: String
)


data class LoginRequest(
    val email: String,
    val password: String
)

data class UserModel(
    val _id: String,
    val username: String,
    val email: String,
    val phone: String,
    val password: String,
    val role: String,
    val isTechnician: Boolean,
    val verified: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)
