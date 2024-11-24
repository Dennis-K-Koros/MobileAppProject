package com.example.mobileappproject.data.apiRepositories

import com.example.mobileappproject.data.api.UserApi
import com.example.mobileappproject.data.dao.UserDao
import com.example.mobileappproject.data.entities.LoginRequest
import com.example.mobileappproject.data.entities.User
import com.example.mobileappproject.data.entities.UserModel
import com.example.mobileappproject.data.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class ApiUserRepository(
    private val userDao: UserDao,
    private val api: UserApi
) : UserRepository {

    // Fetch user by ID and observe changes using Flow
    override fun getUserStream(userId: String): Flow<User?> = userDao.getUser(userId)

    // Insert a user into the database
    override suspend fun insertUser(user: User) = userDao.insert(user)

    // Delete a user from the database
    override suspend fun deleteUser(user: User) = userDao.delete(user)

    // Update a user in the database
    override suspend fun updateUser(user: User) = userDao.update(user)

    // Convert UserModel to User
    private fun mapToUserEntity(userModel: UserModel): User {
        return User(
            id = userModel._id, // Assuming _id is the unique identifier
            username = userModel.username,
            email = userModel.email,
            phone = userModel.phone,
            password = userModel.password,
            role = userModel.role,
            isTechnician = userModel.isTechnician,
            verified = userModel.verified,
            createdAt = userModel.createdAt, // You might need to parse this if it's a String
            updatedAt = userModel.updatedAt // You might need to parse this if it's a String
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
                response.body()?.let { userModel ->
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
        return try {
            val response = api.signUp(user)
            if (response.isSuccessful) {
                response.body()?.let { userModel ->
                    val userEntity = mapToUserEntity(userModel) // Convert to User entity
                    userDao.insert(userEntity) // Cache the new user locally
                    userEntity // Return the User entity
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

