package android.example.tableforone.meal.recipe

import androidx.room.*

@Dao
interface MealRecipeDAO {
    @Query("SELECT * FROM MealRecipe WHERE idMeal = :id")
    fun getMealRecipeById(id : Long): MealRecipe

    @Transaction
    fun updateData(recipe: MealRecipe): Long {
        deleteRecipe(recipe.idMeal)
        return insert(recipe)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: MealRecipe): Long

    @Query("DELETE FROM MealRecipe WHERE idMeal = :idMeal")
    fun deleteRecipe(idMeal: Long)

}