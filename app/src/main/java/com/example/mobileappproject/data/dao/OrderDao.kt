package com.example.mobileappproject.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mobileappproject.data.entities.Order
import kotlinx.coroutines.flow.Flow


@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: Order)

    @Update
    suspend fun update(order: Order)

    @Delete
    suspend fun delete(order: Order)

    @Query("SELECT * from `order` WHERE customer = :id")
    fun getOrders(id: Int): Flow<List<Order>>


    @Query("SELECT * from `order` WHERE customer = :id AND service = :serviceId")
    fun getSpecificOrder(id: Int, serviceId: Int): Flow<Order>

}