package studio.alphared.tableforone.meal.reminder

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [MealReminder::class], version = 1)
abstract class MealReminderDatabase : RoomDatabase() {
    abstract fun mealReminderDao(): MealReminderDAO

    companion object {
        private var instance: MealReminderDatabase? = null

        fun getInstance(context: Context): MealReminderDatabase {
            if (instance == null) {
                instance = Room
                        .databaseBuilder(
                                context.applicationContext,
                                MealReminderDatabase::class.java,
                                "meal-reminder-db"
                        )
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return instance!!
        }
    }
}