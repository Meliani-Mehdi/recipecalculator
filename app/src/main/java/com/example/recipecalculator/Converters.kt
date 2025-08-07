package com.example.recipecalculator

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromIngredientUsageList(value: List<IngredientUsage>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toIngredientUsageList(value: String): List<IngredientUsage> {
        val listType = object : TypeToken<List<IngredientUsage>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
