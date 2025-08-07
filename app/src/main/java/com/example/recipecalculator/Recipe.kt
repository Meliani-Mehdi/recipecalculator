package com.example.recipecalculator

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "ingredients")
    val ingredients: List<IngredientUsage>, // Stored via TypeConverter

    @ColumnInfo(name = "image")
    val image: Int = 0 // Drawable resource ID
)
