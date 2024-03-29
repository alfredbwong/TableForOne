package studio.alphared.tableforone.objects.category

import android.content.Context
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