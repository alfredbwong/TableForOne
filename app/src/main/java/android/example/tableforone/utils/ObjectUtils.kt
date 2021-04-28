package android.example.tableforone.utils

import android.example.tableforone.meal.recipe.MealRecipe

fun convertRawMealRecipeIngredients(recipe: MealRecipe) :  List<String> {
    val listIngredients = mutableListOf(recipe.strIngredient1, recipe.strIngredient2, recipe.strIngredient3, recipe.strIngredient4, recipe.strIngredient5, recipe.strIngredient6, recipe.strIngredient7, recipe.strIngredient8, recipe.strIngredient9, recipe.strIngredient10, recipe.strIngredient11, recipe.strIngredient12, recipe.strIngredient13, recipe.strIngredient14, recipe.strIngredient15, recipe.strIngredient16, recipe.strIngredient17, recipe.strIngredient18, recipe.strIngredient19, recipe.strIngredient20)

    for (i in listIngredients.indices) {
        if (listIngredients[i].isBlank() && listIngredients[i] == "null") {
            listIngredients.removeAt(i)
        }
    }
    return listIngredients
}

fun convertRawMealRecipeMeasurements(recipe: MealRecipe) : List<String>{
    val listMeasures = mutableListOf(recipe.strMeasure1
            ,recipe.strMeasure2
            ,recipe.strMeasure3
            ,recipe.strMeasure4
            ,recipe.strMeasure5
            ,recipe.strMeasure6
            ,recipe.strMeasure7
            ,recipe.strMeasure8
            ,recipe.strMeasure9
            ,recipe.strMeasure10
            ,recipe.strMeasure11
            ,recipe.strMeasure12
            ,recipe.strMeasure13
            ,recipe.strMeasure14
            ,recipe.strMeasure15
            ,recipe.strMeasure16
            ,recipe.strMeasure17
            ,recipe.strMeasure18
            ,recipe.strMeasure19
            ,recipe.strMeasure20)

    for (i in listMeasures.indices) {
        if (listMeasures[i].isBlank() && listMeasures[i] == "null") {
            listMeasures.removeAt(i)
        }
    }
    return listMeasures
}