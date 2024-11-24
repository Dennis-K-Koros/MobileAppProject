package com.example.mobileappproject.api

import com.example.mobileappproject.data.api.CategoryApi
import com.example.mobileappproject.data.api.FavouriteApi
import com.example.mobileappproject.data.api.OrderApi
import com.example.mobileappproject.data.api.ServiceApi
import com.example.mobileappproject.data.api.UserApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "https://mobileappprojectserver.onrender.com" // Replace with your server's URL

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val categoryApi: CategoryApi by lazy {
        retrofit.create(CategoryApi::class.java)
    }
    val favouriteApi: FavouriteApi by lazy {
        retrofit.create(FavouriteApi::class.java)
    }
    val orderApi: OrderApi by lazy {
        retrofit.create(OrderApi::class.java)
    }
    val serviceApi: ServiceApi by lazy {
        retrofit.create(ServiceApi::class.java)
    }
    val userApi: UserApi by lazy {
        retrofit.create(UserApi::class.java)
    }
}
