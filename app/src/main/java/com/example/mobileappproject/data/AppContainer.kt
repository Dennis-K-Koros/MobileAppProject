package com.example.mobileappproject.data

import android.content.Context
import com.example.mobileappproject.api.RetrofitInstance
import com.example.mobileappproject.data.apiRepositories.ApiCategoryRepository
import com.example.mobileappproject.data.apiRepositories.ApiFavouriteRepository
import com.example.mobileappproject.data.apiRepositories.ApiOrderRepository
import com.example.mobileappproject.data.apiRepositories.ApiServiceRepository
import com.example.mobileappproject.data.apiRepositories.ApiUserRepository
import com.example.mobileappproject.data.repositories.CategoryRepository
import com.example.mobileappproject.data.repositories.FavouriteRepository
import com.example.mobileappproject.data.repositories.OrderRepository
import com.example.mobileappproject.data.repositories.ServiceRepository
import com.example.mobileappproject.data.repositories.UserRepository


    /**
     * App container for Dependency injection.
     */
    interface AppContainer {
        val categoryRepository: CategoryRepository
        val favouriteRepository: FavouriteRepository
        val orderRepository: OrderRepository
        val serviceRepository: ServiceRepository
        val userRepository: UserRepository
    }


    class AppDataContainer(private val context: Context) : AppContainer {
        private val database = KaziHubDatabase.getDatabase(context)

        override val categoryRepository: CategoryRepository by lazy {
            ApiCategoryRepository(database.CategoryDao(),RetrofitInstance.categoryApi)
        }
        override val favouriteRepository: FavouriteRepository by lazy {
            ApiFavouriteRepository(database.FavouriteDao(), RetrofitInstance.favouriteApi)
        }
        override val orderRepository: OrderRepository by lazy {
            ApiOrderRepository(database.OrderDao(), RetrofitInstance.orderApi)
        }
        override val serviceRepository: ServiceRepository by lazy {
            ApiServiceRepository(database.ServiceDao(), RetrofitInstance.serviceApi)
        }
        override val userRepository: UserRepository by lazy {
            ApiUserRepository(database.UserDao(), RetrofitInstance.userApi)
        }
    }
