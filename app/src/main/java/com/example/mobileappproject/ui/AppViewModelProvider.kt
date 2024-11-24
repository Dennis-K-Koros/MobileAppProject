package com.example.mobileappproject.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobileappproject.data.repositories.*
import com.example.mobileappproject.viewmodels.CategoriesViewModel
import com.example.mobileappproject.viewmodels.FavouriteViewModel
import com.example.mobileappproject.viewmodels.OrderViewModel
import com.example.mobileappproject.viewmodels.ServiceViewModel
import com.example.mobileappproject.viewmodels.UserViewModel

class AppViewModelProvider(
    private val categoryRepository: CategoryRepository,
    private val favouriteRepository: FavouriteRepository,
    private val orderRepository: OrderRepository,
    private val serviceRepository: ServiceRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CategoriesViewModel::class.java) -> {
                CategoriesViewModel(categoryRepository) as T
            }
            modelClass.isAssignableFrom(FavouriteViewModel::class.java) -> {
                FavouriteViewModel(favouriteRepository) as T
            }
            modelClass.isAssignableFrom(OrderViewModel::class.java) -> {
                OrderViewModel(orderRepository) as T
            }
            modelClass.isAssignableFrom(ServiceViewModel::class.java) -> {
                ServiceViewModel(serviceRepository) as T
            }
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                UserViewModel(userRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
