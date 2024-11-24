package com.example.mobileappproject


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileappproject.api.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ApiTestViewModel : ViewModel() {

    fun testApi() {
        viewModelScope.launch {
            try {
                // Make an API request
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstance.categoryApi.getCategories() // Replace with your API call
                }

                // Log the response for debugging
                if (response.isSuccessful) {
                    Log.d("ApiTest", "API Response: ${response.body()}")
                } else {
                    Log.e("ApiTest", "API Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("ApiTest", "API Exception: ${e.message}", e)
            }
        }
    }
}
