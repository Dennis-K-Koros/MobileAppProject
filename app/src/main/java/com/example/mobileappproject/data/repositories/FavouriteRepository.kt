package com.example.mobileappproject.data.repositories

import com.example.mobileappproject.data.entities.Favourite
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {
    fun getFavouriteStream(userId: Int): Flow<List<Favourite?>>
    suspend fun insertFavourite(favourite: Favourite)
    suspend fun deleteFavourite(favourite: Favourite)
    suspend fun updateFavourite(favourite: Favourite)
    suspend fun fetchFavouritesFromApi(userId: Int): List<Favourite>

}