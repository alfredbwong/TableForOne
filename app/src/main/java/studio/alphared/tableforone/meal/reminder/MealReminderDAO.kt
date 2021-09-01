package studio.alphared.tableforone.meal.reminder

import androidx.room.*

@Dao
interface MealReminderDAO {
    @Query("SELECT * FROM MealReminder")
    fun getMealReminders(): List<MealReminder>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateData(reminders: List<MealReminder>) : List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(reminder: MealReminder) : Long

    @Query( "SELECT * FROM MealReminder WHERE id = :mealReminderId")
    fun getMealReminderById(mealReminderId: Long): MealReminder

    @Query("DELETE FROM MealReminder")
    fun deleteAll()

    @Query("DELETE FROM MealReminder WHERE id = :mealReminderId")
    fun deleteMealReminderById(mealReminderId: Long)

}