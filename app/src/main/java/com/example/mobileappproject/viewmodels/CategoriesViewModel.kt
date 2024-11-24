package com.example.mobileappproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileappproject.data.entities.Category
import com.example.mobileappproject.data.repositories.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoriesViewModel(private val repository: CategoryRepository) : ViewModel() {

    // StateFlow to observe the list of categories
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories

    // StateFlow to observe a single category (e.g., for details)
    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> get() = _selectedCategory

    // Error message flow for observing errors (optional)
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    init {
        // Load categories from the local database on initialization
        loadCategories()
    }

    // Fetch categories from the API and update the database
    fun fetchCategoriesFromApi() {
        viewModelScope.launch {
            try {
                val categoriesFromApi = repository.fetchCategoriesFromApi()
                _categories.value = categoriesFromApi
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching categories: ${e.message}"
            }
        }
    }

    // Load categories from the local database
    private fun loadCategories() {
        viewModelScope.launch {
            repository.getAllCategoryStream().collect { categoryList ->
                _categories.value = categoryList
            }
        }
    }

    // Get a single category by its ID
    fun getCategoryById(id: String) {
        viewModelScope.launch {
            repository.getCategoryStream(id).collect { category ->
                _selectedCategory.value = category
            }
        }
    }

    // Insert a new category
    fun insertCategory(category: Category) {
        viewModelScope.launch {
            try {
                repository.insertCategory(category)
            } catch (e: Exception) {
                _errorMessage.value = "Error inserting category: ${e.message}"
            }
        }
    }

    // Update an existing category
    fun updateCategory(category: Category) {
        viewModelScope.launch {
            try {
                repository.updateCategory(category)
            } catch (e: Exception) {
                _errorMessage.value = "Error updating category: ${e.message}"
            }
        }
    }

    // Delete a category
    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            try {
                repository.deleteCategory(category)
            } catch (e: Exception) {
                _errorMessage.value = "Error deleting category: ${e.message}"
            }
        }
    }
}
