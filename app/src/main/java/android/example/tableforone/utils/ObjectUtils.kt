package android.example.tableforone.utils

import android.example.tableforone.meal.recipe.MealRecipe

fun convertRawMealRecipeIngredients(recipe: MealRecipe) :  List<String> {
    val listIngredients = recipe.ingredients as ArrayList

    for (i in listIngredients.indices) {
        if (listIngredients[i].isBlank() && listIngredients[i] == "null") {
            listIngredients.removeAt(i)
        }
    }
    return listIngredients
}

fun convertRawMealRecipeMeasurements(recipe: MealRecipe) : List<String>{
    val listMeasures = recipe.measurements as ArrayList

    for (i in listMeasures.indices) {
        if (listMeasures[i].isBlank() && listMeasures[i] == "null") {
            listMeasures.removeAt(i)
        }
    }
    return listMeasures
}