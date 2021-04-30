package android.example.tableforone.utils

import android.example.tableforone.meal.recipe.MealRecipe
import androidx.room.TypeConverter
import com.google.gson.Gson


object Converters {
    @TypeConverter
    fun listToJson(value: List<MealRecipe>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<MealRecipe>::class.java).toList()
}