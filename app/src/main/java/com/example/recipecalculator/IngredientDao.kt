package com.example.recipecalculator

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ingredient: Ingredient): Long

    @Update
    suspend fun update(ingredient: Ingredient)

    @Delete
    suspend fun delete(ingredient: Ingredient)

    @Query("SELECT * FROM ingredients ORDER BY name ASC")
    suspend fun getAll(): List<Ingredient>

    @Query("SELECT * FROM ingredients WHERE name LIKE :search")
    suspend fun searchByName(search: String): List<Ingredient>

    @Query("SELECT * FROM ingredients WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Ingredient?
}
