package android.example.tableforone.meal.database

import android.content.Context
import android.example.tableforone.mealCateorySelect.MealCategory
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [MealCategory::class], version = 1)
abstract class MealCategoryDatabase : RoomDatabase() {
    abstract fun mealCategoryDao(): MealCategoryDAO

    companion object {
        private var instance: MealCategoryDatabase? = null

        fun getInstance(context: Context): MealCategoryDatabase {
            if (instance == null) {
                instance = Room
                        .databaseBuilder(
                                context.applicationContext,
                                MealCategoryDatabase::class.java,
                                "meal-category-db"
                        )
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return instance!!
        }
    }
}