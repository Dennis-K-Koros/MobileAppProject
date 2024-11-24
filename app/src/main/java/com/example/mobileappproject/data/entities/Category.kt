package com.example.mobileappproject.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "category")
data class Category(
    @PrimaryKey val id: Int = 0,
    val name: String,
    val subcategories: List<String>, // List of Strings for Room
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)


data class Subcategory(
    val name: String
)
