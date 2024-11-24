package com.example.mobileappproject.data.api

import com.example.mobileappproject.data.entities.Service
import com.example.mobileappproject.data.entities.ServiceResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ServiceApi {

    @GET("/service/")
    suspend fun getService(): Response<ServiceResponse>

    @POST("/service/create")
    suspend fun createService(@Body service: Service): Response<ServiceResponse>

    @PUT("/service/update/{id}")
    suspend fun updateService(@Path("id") id: String, @Body service: Service): Response<ServiceResponse>

    @DELETE("/service/delete/{id}")
    suspend fun deleteService(@Path("id") id: String): Response<Void>
}
