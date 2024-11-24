package com.example.mobileappproject.data.api

import com.example.mobileappproject.data.entities.Category
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoryApi {
    @GET("category/") // Adjust to your endpoint
    suspend fun getCategories(): Response <List<Category>>

    @GET("category/{id}")
    suspend fun getCategoryById(@Path("id") id: String): Response<Category>

    @POST("category/create")
    suspend fun createCategory(@Body category: Category): Response <Category>

    @PUT("category/update/{id}")
    suspend fun updateCategory(@Path("id") id: String, @Body category: Category): Response<Category>

    @DELETE("category/delete/{id}")
    suspend fun deleteCategory(@Path("id") id: String): Response <Void>
}