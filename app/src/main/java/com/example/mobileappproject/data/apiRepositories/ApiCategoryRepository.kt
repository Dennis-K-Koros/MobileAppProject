package com.example.mobileappproject.data.apiRepositories

import com.example.mobileappproject.data.api.CategoryApi
import com.example.mobileappproject.data.dao.CategoryDao
import com.example.mobileappproject.data.entities.Category
import com.example.mobileappproject.data.entities.CategoryModel
import com.example.mobileappproject.data.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class ApiCategoryRepository(
    private val categoryDao: CategoryDao,
    private val api: CategoryApi
) : CategoryRepository {

    override fun getAllCategoryStream(): Flow<List<Category>> = categoryDao.getAllCategory()

    override fun getCategoryStream(id: String): Flow<Category?> = categoryDao.getCategory(id)

    override suspend fun insertCategory(category: Category) = categoryDao.insert(category)

    override suspend fun deleteCategory(category: Category) = categoryDao.delete(category)

    override suspend fun updateCategory(category: Category) = categoryDao.update(category)

    override suspend fun fetchCategoriesFromApi(): List<Category> {
        return try {
            val response = api.getCategories()
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.status == "SUCCESS") {
                    val categories = body.data.map { mapToCategoryEntity(it) }
                    categoryDao.insertAll(categories) // Save to Room
                    categories // Return the saved entities
                } else {
                    throw Exception("Failed to fetch categories: ${body?.status}")
                }
            } else {
                throw Exception("API call failed: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Error in fetchCategoriesFromApi: ${e.message}")
            categoryDao.getAllCategory().first() // Return cached categories on failure
        }
    }

    // Mapper function to convert API model to Room entity
    private fun mapToCategoryEntity(categoryModel: CategoryModel): Category {
        return Category(
            id = categoryModel._id,
            name = categoryModel.categoryName,
            subcategories = categoryModel.subcategories.map { it.name }.joinToString(","), // Convert list to comma-separated string
            createdAt = categoryModel.createdAt,
            updatedAt = categoryModel.updatedAt
        )
    }
}
