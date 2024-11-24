package com.example.mobileappproject.data.apiRepositories

import com.example.mobileappproject.data.api.UserApi
import com.example.mobileappproject.data.dao.UserDao
import com.example.mobileappproject.data.entities.LoginRequest
import com.example.mobileappproject.data.entities.User
import com.example.mobileappproject.data.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class ApiUserRepository(
    private val userDao: UserDao,
    private val api: UserApi
) : UserRepository {
    override fun getUserStream(userId: String): Flow<User?> = userDao.getUser(userId)

    override suspend fun insertUser(user: User) = userDao.insert(user)

    override suspend fun deleteUser(user: User) = userDao.delete(user)

    override suspend fun updateUser(user: User) = userDao.update(user)

    override suspend fun fetchUserFromApi(userId: String): User? {
        return try {
            val response = api.getUserProfile(userId.toString())
            if (response.isSuccessful) {
                response.body()?.let { user ->
                    userDao.insert(user) // Save API user to local DB
                    user
                }
            } else null
        } catch (e: Exception) {
            println("Exception: ${e.message}") // Log the exception
            null
        }
    }

    override suspend fun loginUser(email: String, password: String): User? {
        return try {
            val response = api.signIn(LoginRequest(email = email, password = password))
            if (response.isSuccessful) {
                response.body()?.let { user ->
                    userDao.insert(user) // Cache the user locally
                    user
                }
            } else null
        } catch (e: Exception) {
            println("Login exception: ${e.message}")
            null
        }
    }

    override suspend fun signupUser(user: User): User? {
        return try {
            val response = api.signUp(user)
            if (response.isSuccessful) {
                response.body()?.let { newUser ->
                    userDao.insert(newUser) // Cache the new user locally
                    newUser
                }
            } else null
        } catch (e: Exception) {
            println("Signup exception: ${e.message}")
            null
        }
    }
}
