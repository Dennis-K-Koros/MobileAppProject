package com.example.mobileappproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobileappproject.data.repositories.FavouriteRepository

class FavouriteViewModelFactory(
    private val favouriteRepository: FavouriteRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavouriteViewModel(favouriteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}