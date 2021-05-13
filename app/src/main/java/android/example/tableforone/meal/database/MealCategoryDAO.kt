package android.example.tableforone.meal.database

import android.example.tableforone.meal.category.MealCategory
import androidx.room.*
/**
 * Data Access Object interface for the meal categories.
 */
@Dao
interface MealCategoryDAO {
    @Query("SELECT * FROM MealCategory")
    fun getMealCategories(): List<MealCategory>

    @Transaction
    fun updateData(mealCategories : List<MealCategory>): List<Long> {
        deleteAllCategories()
        return insertAll(mealCategories)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(mealCategories: List<MealCategory>): List<Long>

    @Query("DELETE FROM MealCategory")
    fun deleteAllCategories()

}