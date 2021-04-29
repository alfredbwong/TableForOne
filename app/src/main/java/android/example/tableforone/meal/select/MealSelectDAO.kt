package android.example.tableforone.meal.select

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MealSelectDAO {
    @Query("SELECT * FROM MealSelectItem")
    fun getMealSelectItem(): List<MealSelectItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateData(data: List<MealSelectItem>): List<Long>

}