package studio.alphared.tableforone.meal.select

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MealCategoryItem (@PrimaryKey (autoGenerate = true) var id : Long = 0,
                             val strMeal : String,
                             val strMealThumb: String,
                             val idMeal : Long,
                             val idCategoryStr : String
    )