package android.example.tableforone.meal.reminder

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MealReminderDAO {
    @Query("SELECT * FROM MealReminder")
    fun getMealReminders(): List<MealReminder>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateData(reminders: List<MealReminder>) : List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(reminders: MealReminder)
}