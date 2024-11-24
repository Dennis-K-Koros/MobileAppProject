package com.example.mobileappproject.data.apiRepositories

import com.example.mobileappproject.data.api.ServiceApi
import com.example.mobileappproject.data.dao.ServiceDao
import com.example.mobileappproject.data.entities.Service
import com.example.mobileappproject.data.repositories.ServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class ApiServiceRepository(
    private val serviceDao: ServiceDao,
    private val api: ServiceApi
) : ServiceRepository {
    override fun getAllServiceStream(): Flow<List<Service>> = serviceDao.getAllServices()
    override fun getServiceStream(id: Int): Flow<Service?> =serviceDao.getService(id)
    override suspend fun insertService(service: Service) = serviceDao.insert(service)
    override suspend fun deleteService(service: Service) = serviceDao.delete(service)
    override suspend fun updateService(service: Service) = serviceDao.update(service)
    override suspend fun fetchServiceFromApi(): List<Service> {
        return try {
            val response = api.getService()
            if (response.isSuccessful && response.body() != null) {
                response.body()?.let { services ->
                    serviceDao.insert(services) // Save API favourites to local DB
                }
                serviceDao.getAllServices().first() // Return cached favourites
            } else {
                serviceDao.getAllServices().first() // Fallback to cached data
            }
        } catch (e: Exception) {
            // Log or handle the exception
            serviceDao.getAllServices().first() // Return cached favourites
        }
    }
}