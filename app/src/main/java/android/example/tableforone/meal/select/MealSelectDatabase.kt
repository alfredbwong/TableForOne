package android.example.tableforone.meal.select

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MealSelectItem::class], version = 1)
abstract class MealSelectDatabase : RoomDatabase() {
    abstract fun mealSelectDao(): MealSelectDAO

    companion object {
        private var instance: MealSelectDatabase? = null

        fun getInstance(context: Context): MealSelectDatabase {
            if (instance == null) {
                instance = Room
                        .databaseBuilder(
                                context.applicationContext,
                                MealSelectDatabase::class.java,
                                "meal-select-db"
                        )
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return instance!!
        }
    }
}