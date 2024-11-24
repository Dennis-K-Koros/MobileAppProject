package com.example.mobileappproject.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.mobileappproject.data.converters.DateConverter
import java.util.Date


@Entity(tableName = "Service")
 data class Service(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Auto-generates the ID
    val subcategory: String,
    val serviceName: String,
    val description: String,
    val price: Int,
    val image: String? = null,         // Nullable if no image is provided
    @TypeConverters(DateConverter::class) val createdAt: Date,
    @TypeConverters(DateConverter::class) val updatedAt: Date
 )
