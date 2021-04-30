package android.example.tableforone.meal.reminder

import androidx.room.Dao
import androidx.room.Query

@Dao
interface MealReminderDAO {
    @Query("SELECT * FROM MealReminder")
    fun getMealReminders(): List<MealReminder>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(reminder: MealReminder) : List<Long>

}