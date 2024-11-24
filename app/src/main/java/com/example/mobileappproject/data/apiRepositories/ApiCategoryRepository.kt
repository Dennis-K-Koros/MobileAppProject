package com.example.mobileappproject.data.apiRepositories

import com.example.mobileappproject.data.api.CategoryApi
import com.example.mobileappproject.data.dao.CategoryDao
import com.example.mobileappproject.data.entities.Category
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
        val response = api.getCategories()
        if (response.isSuccessful) {
            response.body()?.let { categories ->
                // Save API results to local database
                categories.forEach { categoryDao.insert(it) }
            }
        }
        return categoryDao.getAllCategory().first() // Return cached categories
    }
}
