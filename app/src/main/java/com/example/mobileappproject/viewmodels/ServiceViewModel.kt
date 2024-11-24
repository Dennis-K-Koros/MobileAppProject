package com.example.mobileappproject.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileappproject.data.entities.Service
import com.example.mobileappproject.data.repositories.ServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.State

class ServiceViewModel(private val repository: ServiceRepository) : ViewModel() {

    private val _services = MutableStateFlow<List<Service>>(emptyList())
    val services: StateFlow<List<Service>> get() = _services


    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun fetchServices() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val fetchedServices = repository.fetchServiceFromApi()
                _services.value = fetchedServices
            }catch (e: Exception){
                _errorMessage.value = "Error fetching categories: ${e.message}"
            }finally {
                _isLoading.value = false
            }

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
