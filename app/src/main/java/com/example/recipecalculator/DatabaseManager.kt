package com.example.recipecalculator

import android.content.Context

class DatabaseManager(context: Context) {
    private val db = AppDatabase.getInstance(context)
    private val ingredientDao = db.ingredientDao()
    private val recipeDao = db.recipeDao()

    // Ingredient Operations
    suspend fun addIngredient(ingredient: Ingredient) = ingredientDao.insert(ingredient)
    suspend fun updateIngredient(ingredient: Ingredient) = ingredientDao.update(ingredient)
    suspend fun deleteIngredient(ingredient: Ingredient) = ingredientDao.delete(ingredient)
    suspend fun getAllIngredients() = ingredientDao.getAll()
    suspend fun findIngredientByName(name: String) = ingredientDao.searchByName("%$name%")
    suspend fun getIngredientById(id: Int) = ingredientDao.getById(id)

    // Recipe Operations
    suspend fun addRecipe(recipe: Recipe) = recipeDao.insert(recipe)
    suspend fun updateRecipe(recipe: Recipe) = recipeDao.update(recipe)
    suspend fun deleteRecipe(recipe: Recipe) = recipeDao.delete(recipe)
    suspend fun getAllRecipes() = recipeDao.getAll()
    suspend fun getRecipeById(id: Int) = recipeDao.getById(id)
}
