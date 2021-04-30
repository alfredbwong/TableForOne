package android.example.tableforone.meal.recipe

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MealRecipeDAO {
    @Query("SELECT * FROM MealRecipe")
    fun getMealRecipes(): List<MealRecipe>

    @Query("SELECT * FROM MealRecipe WHERE idMeal = :id")
    fun getMealRecipeById(id : Long): MealRecipe

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateData(data: MealRecipe): Long

}