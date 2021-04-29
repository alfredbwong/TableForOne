package android.example.tableforone.mealCateorySelect

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class MealCategory(
        @PrimaryKey val idCategory : String,
        val strCategory: String,
        @Json(name="strCategoryThumb")
    val categoryImg: String
)
