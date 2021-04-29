package android.example.tableforone.meal.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MealReminderDAO {
    @Query("SELECT * FROM MealReminder")
    fun getMealReminders(): List<MealReminder>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(reminder: MealReminder) : List<Long>

}