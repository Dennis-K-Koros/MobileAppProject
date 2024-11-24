package com.example.mobileappproject.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mobileappproject.data.entities.CategoriesResponse
import com.example.mobileappproject.data.entities.Category
import kotlinx.coroutines.flow.Flow


@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<Category>)

    @Update
    suspend fun update(category: Category)

    @Delete
    suspend fun delete(category: Category)

    @Query("SELECT * FROM category WHERE id = :id")
    fun getCategory(id: String): Flow<Category?>

    @Query("SELECT * FROM category ORDER BY name ASC")
    fun getAllCategory(): Flow<List<Category>>
}
