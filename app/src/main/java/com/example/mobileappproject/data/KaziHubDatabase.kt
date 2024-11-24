package com.example.mobileappproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mobileappproject.data.dao.CategoryDao
import com.example.mobileappproject.data.dao.FavouriteDao
import com.example.mobileappproject.data.dao.OrderDao
import com.example.mobileappproject.data.dao.ServiceDao
import com.example.mobileappproject.data.dao.UserDao
import com.example.mobileappproject.data.entities.Category
import com.example.mobileappproject.data.entities.Favourite
import com.example.mobileappproject.data.entities.Order
import com.example.mobileappproject.data.entities.Service
import com.example.mobileappproject.data.entities.User


@Database(entities = [Favourite::class, Order::class, Service::class, User::class, Category::class], version = 1, exportSchema = false)
abstract class KaziHubDatabase : RoomDatabase() {

    abstract fun CategoryDao(): CategoryDao
    abstract fun FavouriteDao(): FavouriteDao
    abstract fun OrderDao() : OrderDao
    abstract fun ServiceDao() : ServiceDao
    abstract fun UserDao() : UserDao

    companion object {
        @Volatile
        private var Instance: KaziHubDatabase? = null

        fun getDatabase(context: Context): KaziHubDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, KaziHubDatabase::class.java, "KaziHubDatabase")
                    .build()
                    .also { Instance = it }
            }
        }
    }

}