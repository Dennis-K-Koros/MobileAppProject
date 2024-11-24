package com.example.mobileappproject.data.converters

import androidx.room.TypeConverter
import com.example.mobileappproject.data.entities.Subcategory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SubcategoryConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromSubcategoryList(value: List<Subcategory>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toSubcategoryList(value: String): List<Subcategory> {
        val type = object : TypeToken<List<Subcategory>>() {}.type
        return gson.fromJson(value, type)
    }
}
