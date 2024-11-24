package com.example.mobileappproject.data.api

import com.example.mobileappproject.data.entities.Category
import com.example.mobileappproject.data.entities.Favourite
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavouriteApi {
    @GET("favourite/{id}") // Adjust to your endpoint
    suspend fun getFavourites(@Path("id") id: String): Response<List<Favourite>>

    @POST("favourite/create")
    suspend fun createFavourite(@Body favourite: Favourite): Response<Favourite>

    @DELETE("favourite/delete/{id}")
    suspend fun deleteFavourite(@Path("id") id: String): Response<Void>

}