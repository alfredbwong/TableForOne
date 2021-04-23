package android.example.tableforone.mealSelect

import com.squareup.moshi.Json

data class MealCategory(
    val idCategory : String,
    val strCategory: String,
    @Json(name="strCategoryThumb")
    val categoryImg: String
)
