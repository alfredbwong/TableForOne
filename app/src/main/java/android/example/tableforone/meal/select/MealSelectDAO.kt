package android.example.tableforone.meal.select

import android.example.tableforone.meal.recipe.MealRecipe
import androidx.room.*

@Dao
interface MealSelectDAO {
    @Query("SELECT * FROM MealSelectItem WHERE idCategoryStr = :category")
    fun getMealSelectItem(category : String): List<MealSelectItem>


    @Transaction
    fun updateData(category: String, mealCategoryItems : List<MealSelectItem>): List<Long> {
        deleteCategoryItems(category)
        return insert(mealCategoryItems)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: List<MealSelectItem>): List<Long>

    @Query("DELETE FROM MealSelectItem WHERE idCategoryStr = :category")
    fun deleteCategoryItems(category : String)

}