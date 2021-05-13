package android.example.tableforone.meal.recipe

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MealRecipe::class], version = 1)
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