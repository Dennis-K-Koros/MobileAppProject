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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(orders: List<Order>)

    @Delete
    suspend fun delete(order: Order)

    @Update
    suspend fun update(order: Order)

    @Query("SELECT * FROM `Order` WHERE userId = :userId")
    fun getOrders(userId: String): Flow<List<Order>>

    @Query("SELECT * FROM `Order` WHERE userId = :userId AND service = :serviceId")
    fun getSpecificOrder(userId: String, serviceId: String): Flow<Order?>
}
