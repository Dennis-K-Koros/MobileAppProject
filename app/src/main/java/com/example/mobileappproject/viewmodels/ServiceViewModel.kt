package com.example.mobileappproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileappproject.data.entities.Service
import com.example.mobileappproject.data.repositories.ServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ServiceViewModel(private val repository: ServiceRepository) : ViewModel() {

    private val _services = MutableStateFlow<List<Service>>(emptyList())
    val services: StateFlow<List<Service>> get() = _services

    fun fetchServices() {
        viewModelScope.launch {
            val fetchedServices = repository.fetchServiceFromApi()
            _services.value = fetchedServices
        }
    }

    fun insertService(service: Service) {
        viewModelScope.launch {
            repository.insertService(service)
            fetchServices() // Refresh services after insertion
        }
    }

    fun deleteService(service: Service) {
        viewModelScope.launch {
            repository.deleteService(service)
            fetchServices() // Refresh services after deletion
        }
    }
}
