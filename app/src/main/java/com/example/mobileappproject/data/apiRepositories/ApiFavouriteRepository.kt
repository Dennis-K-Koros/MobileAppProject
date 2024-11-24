package com.example.mobileappproject.data.apiRepositories

import com.example.mobileappproject.data.api.FavouriteApi
import com.example.mobileappproject.data.dao.FavouriteDao
import com.example.mobileappproject.data.entities.Favourite
import com.example.mobileappproject.data.repositories.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class ApiFavouriteRepository(
    private val favouriteDao: FavouriteDao,
    private val api: FavouriteApi
) : FavouriteRepository {
    // Stream a list of favourites for the user
    override fun getFavouriteStream(userId: String): Flow<List<Favourite>> =
        favouriteDao.getFavourites(userId)

    override suspend fun insertFavourite(favourite: Favourite) =
        favouriteDao.insert(favourite)

    override suspend fun deleteFavourite(favourite: Favourite) =
        favouriteDao.delete(favourite)

    override suspend fun updateFavourite(favourite: Favourite) =
        favouriteDao.update(favourite)

    override suspend fun fetchFavouritesFromApi(userId: String): List<Favourite> {
        return try {
            val response = api.getFavourites(userId)
            if (response.isSuccessful && response.body() != null) {
                response.body()?.let { favourites ->
                    favouriteDao.insertAll(favourites) // Save API favourites to local DB
                }
                favouriteDao.getFavourites(userId).first() // Return cached favourites
            } else {
                favouriteDao.getFavourites(userId).first() // Fallback to cached data
            }
        } catch (e: Exception) {
            // Log or handle the exception
            favouriteDao.getFavourites(userId).first() // Return cached favourites
        }
    }
}


