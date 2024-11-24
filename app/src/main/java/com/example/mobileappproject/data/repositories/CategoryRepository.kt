package com.example.mobileappproject.data.repositories

import com.example.mobileappproject.data.entities.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategoryStream(): Flow<List<Category>>
    fun getCategoryStream(id: Int): Flow<Category?>
    suspend fun insertCategory(category: Category)
    suspend fun deleteCategory(category: Category)
    suspend fun updateCategory(category: Category)
    suspend fun fetchCategoriesFromApi(): List<Category>
}