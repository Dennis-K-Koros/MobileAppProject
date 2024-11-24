package com.example.mobileappproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobileappproject.data.repositories.ServiceRepository

class ServiceViewModelFactory(
    private val serviceRepository: ServiceRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServiceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ServiceViewModel(serviceRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}