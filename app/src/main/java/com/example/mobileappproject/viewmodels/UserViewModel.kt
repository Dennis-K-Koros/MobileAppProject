package com.example.mobileappproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileappproject.data.entities.User
import com.example.mobileappproject.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> get() = _currentUser

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = repository.loginUser(email, password)
            _currentUser.value = user // Assign User entity directly
            _isLoggedIn.value = user != null
        }
    }

    fun signup(user: User) {
        viewModelScope.launch {
            val newUser = repository.signupUser(user)
            _currentUser.value = newUser // Assign User entity directly
            _isLoggedIn.value = newUser != null
        }
    }


    fun logout() {
        _currentUser.value = null
        _isLoggedIn.value = false
    }

    fun updateProfile(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
            _currentUser.value = user
        }
    }
}
