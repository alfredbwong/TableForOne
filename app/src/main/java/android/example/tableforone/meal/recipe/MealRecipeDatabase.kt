package android.example.tableforone.meal.recipe

import android.content.Context
import android.example.tableforone.utils.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MealRecipe::class], version = 1)
@TypeConverters(Converters::class)
abstract class MealRecipeDatabase : RoomDatabase() {
    abstract fun mealRecipeDao(): MealRecipeDAO

    companion object {
        private var instance: MealRecipeDatabase? = null

        fun getInstance(context: Context): MealRecipeDatabase {
            if (instance == null) {
                instance = Room
                        .databaseBuilder(
                                context.applicationContext,
                                MealRecipeDatabase::class.java,
                                "meal-recipe-db"
                        )
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return instance!!
        }
    }
}