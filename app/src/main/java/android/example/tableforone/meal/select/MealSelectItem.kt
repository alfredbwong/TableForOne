package android.example.tableforone.meal.select

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MealSelectItem (
    val strMeal : String,
    val strMealThumb: String,
    @PrimaryKey val idMeal : Long)