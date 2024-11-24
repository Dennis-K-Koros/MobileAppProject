package com.example.mobileappproject.data.repositories

import com.example.mobileappproject.data.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserStream(userId: String): Flow<User?>
    suspend fun insertUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun fetchUserFromApi(userId: String): User?
    suspend fun loginUser(email: String, password: String): User?
    suspend fun signupUser(user: User): User?
}