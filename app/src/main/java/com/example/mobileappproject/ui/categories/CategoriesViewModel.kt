package com.example.mobileappproject.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobileappproject.data.entities.Category
import com.example.mobileappproject.data.repositories.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val repository: CategoryRepository
) : ViewModel() {
    val categories: LiveData<List<Category>> = repository.getAllCategoryStream().asLiveData()

    fun fetchCategoriesFromApi() = viewModelScope.launch {
        repository.fetchCategoriesFromApi()
    }

    fun addCategory(category: Category) = viewModelScope.launch {
        repository.insertCategory(category)
    }
}
