package com.example.mobileappproject.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileappproject.data.entities.SignupRequest
import com.example.mobileappproject.data.entities.User
import com.example.mobileappproject.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _userId = MutableStateFlow<String?>(null)
    val currentUser: StateFlow<User?> = _userId.flatMapLatest { id ->
        id?.let {
            Log.d("UserViewModel", "Fetching user details for userId: $id")
            repository.getUserStream(it)
        } ?: flowOf(null)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = currentUser.map { it != null }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    // Initialize with a neutral state to prevent infinite loading
    private val _loginResult = MutableStateFlow<Result>(Result.Success)
    val loginResult: StateFlow<Result> get() = _loginResult

    init {
        viewModelScope.launch {
            _userId.collect { userId ->
                Log.d("UserViewModel", "User ID changed to: $userId")
            }
        }
    }

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _loginResult.value = Result.Loading
            Log.d("Login", "Attempting login with email: $email and password: $password")

            try {
                val user = repository.loginUser(email, password)
                if (user != null) {
                    _userId.value = user.id // Use the user's ID to track the current user
                    Log.d("UserViewModel", "User ID set to: ${user.id}")
                    _isLoggedIn.value = true
                    _loginResult.value = Result.Success
                    Log.d("Login", "Login successful. Received user data: $user")
                    onResult(true)
                } else {
                    _loginResult.value = Result.Failure("Invalid username or password")
                    _isLoggedIn.value = false
                    Log.d("Login", "Login failed. Invalid username or password.")
                    onResult(false)
                }
            } catch (e: Exception) {
                _loginResult.value = Result.Failure(e.message ?: "An error occurred")
                Log.e("Login", "Exception occurred during login: ${e.message}", e)
                onResult(false)
            }
        }
    }

    fun signup(signupRequest: SignupRequest, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val user = repository.signupUser(
                    User(
                        id = "", // Will be generated by the backend
                        username = signupRequest.username,
                        email = signupRequest.email,
                        phone = signupRequest.phone,
                        password = signupRequest.password,
                        createdAt = "", // Will be provided by the backend
                        updatedAt = ""  // Will be provided by the backend
                    )
                )
                if (user != null) {
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun logout() {
        _userId.value = null // Reset the user ID to clear the current user
        _isLoggedIn.value = false
    }

    fun updateProfile(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
            _userId.value = user.id // Update the current user ID if necessary
        }
    }
}

// Sealed class to represent the login result states
sealed class Result {
    object Loading : Result()
    object Success : Result()
    data class Failure(val errorMessage: String) : Result()
}
