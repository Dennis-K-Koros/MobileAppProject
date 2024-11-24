package com.example.mobileappproject.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.mobileappproject.data.converters.DateConverter
import java.util.Date


@Entity(tableName = "Favourite")
data class Favourite (
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Auto-generates the ID
    val userId: String,
    val serviceId: String,
    @TypeConverters(DateConverter::class) val createdAt: Date,
    @TypeConverters(DateConverter::class) val updatedAt: Date
)