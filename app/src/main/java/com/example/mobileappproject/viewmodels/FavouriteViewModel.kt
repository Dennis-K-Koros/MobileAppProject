package com.example.mobileappproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileappproject.data.entities.Favourite
import com.example.mobileappproject.data.repositories.FavouriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavouriteViewModel(private val repository: FavouriteRepository) : ViewModel() {

    private val _favourites = MutableStateFlow<List<Favourite>>(emptyList())
    val favourites: StateFlow<List<Favourite>> get() = _favourites

    fun fetchFavourites(userId: String) {
        viewModelScope.launch {
            val fetchedFavourites = repository.fetchFavouritesFromApi(userId)
            _favourites.value = fetchedFavourites
        }
    }

    fun addFavourite(favourite: Favourite) {
        viewModelScope.launch {
            repository.insertFavourite(favourite)
            fetchFavourites(favourite.userId) // Refresh favourites after adding
        }
    }

    fun removeFavourite(favourite: Favourite) {
        viewModelScope.launch {
            repository.deleteFavourite(favourite)
            fetchFavourites(favourite.userId) // Refresh favourites after removal
        }
    }
}
