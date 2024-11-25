package com.example.mobileappproject.data.repositories

import com.example.mobileappproject.data.entities.User
import com.example.mobileappproject.data.entities.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserStream(userId: String): Flow<User?>
    suspend fun insertUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun fetchUserFromApi(userId: String): UserModel?
    suspend fun loginUser(email: String, password: String): User?
    suspend fun signupUser(user: User): User?
}