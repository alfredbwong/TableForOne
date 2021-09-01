package studio.alphared.tableforone.meal.select

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MealCategoryItem::class], version = 1)
abstract class MealCategoryItemDatabase : RoomDatabase() {
    abstract fun mealSelectDao(): MealCategoryItemDAO

    companion object {
        private var instance: MealCategoryItemDatabase? = null

        fun getInstance(context: Context): MealCategoryItemDatabase {
            if (instance == null) {
                instance = Room
                        .databaseBuilder(
                                context.applicationContext,
                                MealCategoryItemDatabase::class.java,
                                "meal-select-db"
                        )
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return instance!!
        }
    }
}