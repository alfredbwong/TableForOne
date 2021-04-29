package android.example.tableforone.meal.recipe

data class MealRecipe (
    val idMeal : Long,
    val strMeal : String,
    val strArea : String,
    val strInstructions : String,
    val strMealThumb : String,
    val ingredients : List<String>,
    val measurements : List<String>
)