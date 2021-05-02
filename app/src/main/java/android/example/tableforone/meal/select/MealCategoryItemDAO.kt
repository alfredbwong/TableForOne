package android.example.tableforone.meal.select

import androidx.room.*

@Dao
interface MealCategoryItemDAO {
    @Query("SELECT * FROM MealCategoryItem WHERE idCategoryStr = :category")
    fun getMealSelectItem(category : String): List<MealCategoryItem>


    @Transaction
    fun updateData(category: String, mealCategoryItems : List<MealCategoryItem>): List<Long> {
        deleteCategoryItems(category)
        return insert(mealCategoryItems)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: List<MealCategoryItem>): List<Long>

    @Query("DELETE FROM MealCategoryItem WHERE idCategoryStr = :category")
    fun deleteCategoryItems(category : String)

}