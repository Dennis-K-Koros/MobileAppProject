package com.example.mobileappproject.data.repositories

import com.example.mobileappproject.data.entities.Service
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {
    fun getAllServiceStream(): Flow<List<Service>>
    fun getServiceStream(id: Int): Flow<Service?>
    suspend fun insertService(service: Service)
    suspend fun deleteService(service: Service)
    suspend fun updateService(service: Service)
    suspend fun fetchServiceFromApi(): List<Service>
}