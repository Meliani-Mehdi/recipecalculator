package com.example.recipecalculator

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ingredients",
    indices = [Index(value = ["name"], unique = true)]
)
data class ingredient(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "unit_type")
    val unitType: String, // e.g., "1", "1Kg", "1g", "1L"

    @ColumnInfo(name = "price_per_unit")
    val pricePerUnit: Double,

    @ColumnInfo(name = "image")
    val image: Int = 0 // 0 = no image; otherwise, drawable resource ID
)
