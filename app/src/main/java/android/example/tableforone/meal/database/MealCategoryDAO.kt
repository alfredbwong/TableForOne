package android.example.tableforone.meal.database

import android.example.tableforone.mealCateorySelect.MealCategory
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MealCategoryDAO {
    @Query("SELECT * FROM MealCategory")
    fun getMealCategories(): List<MealCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateData(data: List<MealCategory>): List<Long>

}