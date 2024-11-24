package com.example.mobileappproject.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey val id: String,
    val name: String,
    val subcategories: String,
    val createdAt: String,
    val updatedAt: String
)


data class Subcategory(
    val name: String,
    val id: String
)

data class CategoryModel(
    val _id: String,
    val categoryName: String,
    val createdAt: String,
    val updatedAt: String,
    val name: String,
    val subcategories: List<SubcategoryModel>
)

data class SubcategoryModel(
    val _id: String,
    val name: String
)

data class CategoriesResponse(
    val status: String,
    val data: List<CategoryModel> // List of categories
)



