package com.example.mobileappproject.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.mobileappproject.data.converters.DateConverter
import java.util.Date

@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Auto-generates the ID
    val username: String,
    val email: String,
    val phone: String,
    val password: String,
    val role: String = "customer",
    val isTechnician: Boolean = false, // Defaults to false
    val image: String? = null,         // Nullable if no image is provided
    val verified: Boolean = false,     // Changed to Boolean for clarity
    @TypeConverters(DateConverter::class) val createdAt: Date,
    @TypeConverters(DateConverter::class) val updatedAt: Date
)

data class LoginRequest(
    val email: String,
    val password: String
)
