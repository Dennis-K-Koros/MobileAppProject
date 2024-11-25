package com.example.mobileappproject.data.api

import com.example.mobileappproject.data.entities.ApiResponse
import com.example.mobileappproject.data.entities.LoginRequest
import com.example.mobileappproject.data.entities.SignupRequest
import com.example.mobileappproject.data.entities.User
import com.example.mobileappproject.data.entities.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @GET("/user/getProfile/{id}")
    suspend fun getUserProfile(@Path("id") id: String): Response<UserModel>

    @POST("/user/signup")
    suspend fun signUp(@Body user: SignupRequest): Response<UserModel>

    @POST("/user/signin")
    suspend fun signIn(@Body user: LoginRequest): Response<ApiResponse>

    @POST("/user/resendVerificationLink")
    suspend fun resendVerificationLink(@Body user: User): Response<UserModel>

    @PUT("/user/update/{id}")
    suspend fun updateUser(@Path("id") id: String, @Body user: User): Response<UserModel>

    @DELETE("/user/delete/{id}")
    suspend fun deleteUser(@Path("id") id: String): Response<Void>

}