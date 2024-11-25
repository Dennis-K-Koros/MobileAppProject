package com.example.mobileappproject.data.apiRepositories

import android.util.Log
import com.example.mobileappproject.data.api.UserApi
import com.example.mobileappproject.data.dao.UserDao
import com.example.mobileappproject.data.entities.LoginRequest
import com.example.mobileappproject.data.entities.SignupRequest
import com.example.mobileappproject.data.entities.User
import com.example.mobileappproject.data.entities.UserModel
import com.example.mobileappproject.data.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class ApiUserRepository(
    private val userDao: UserDao,
    private val api: UserApi
) : UserRepository {

    // Fetch user by ID and observe changes using Flow
    override suspend fun getUserStream(userId: String): Flow<User> {
        Log.d("UserRepository", "Fetching user stream for userId: $userId")
        return userDao.getUser(userId).also {
            it.collect { user ->
                Log.d("UserRepository", "User fetched: $user")
            }
        }
    }


    // Insert a user into the database
    override suspend fun insertUser(user: User) = userDao.insert(user)

    // Delete a user from the database
    override suspend fun deleteUser(user: User) = userDao.delete(user)

    // Update a user in the database
    override suspend fun updateUser(user: User) = userDao.update(user)

    // Convert UserModel to User
    private fun mapToUserEntity(userModel: UserModel): User {
        return User(
            id = userModel._id ?: throw IllegalArgumentException("User ID cannot be null"),
            username = userModel.username,
            email = userModel.email,
            phone = userModel.phone,
            password = userModel.password,
            role = userModel.role,
            isTechnician = userModel.isTechnician,
            verified = userModel.verified,
            createdAt = userModel.createdAt,
            updatedAt = userModel.updatedAt
        )
    }


    // Fetch user profile from API and store it locally if successful
    override suspend fun fetchUserFromApi(userId: String): UserModel? {
        return try {
            val response = api.getUserProfile(userId)
            if (response.isSuccessful) {
                response.body()?.let { userModel ->
                    val userEntity = mapToUserEntity(userModel) // Convert to User entity
                    userDao.insert(userEntity) // Save API user to local DB
                    userModel
                }
            } else {
                println("Error fetching user profile: ${response.message()}")
                null
            }
        } catch (e: Exception) {
            println("Exception in fetchUserFromApi: ${e.message}")
            null
        }
    }

    // Handle user login, authenticate via API and cache the user locally
    override suspend fun loginUser(email: String, password: String): User? {
        return try {
            val response = api.signIn(LoginRequest(email = email, password = password))
            if (response.isSuccessful) {
                val apiResponse = response.body()
                apiResponse?.data?.firstOrNull()?.let { userModel ->
                    val userEntity = mapToUserEntity(userModel) // Convert to User entity
                    userDao.insert(userEntity) // Cache the user locally
                    userEntity // Return the User entity
                }
            } else {
                println("Error during login: ${response.message()}")
                null
            }
        } catch (e: Exception) {
            println("Login exception: ${e.message}")
            null
        }
    }


    override suspend fun signupUser(user: User): User? {
        val signupRequest = SignupRequest(
            username = user.username,
            email = user.email,
            phone = user.phone,
            password = user.password
        )

        return try {
            val response = api.signUp(signupRequest)
            if (response.isSuccessful) {
                response.body()?.let { userModel ->
                    val userEntity = mapToUserEntity(userModel)
                    userDao.insert(userEntity) // Cache the new user locally
                    userEntity
                }
            } else {
                println("Error during signup: ${response.message()}")
                null
            }
        } catch (e: Exception) {
            println("Signup exception: ${e.message}")
            null
        }
    }

}

