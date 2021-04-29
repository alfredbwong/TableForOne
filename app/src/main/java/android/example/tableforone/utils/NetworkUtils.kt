package android.example.tableforone.utils

import android.example.tableforone.meal.recipe.MealRecipe
import android.example.tableforone.meal.select.MealSelectItem
import android.example.tableforone.mealCateorySelect.MealCategory
import android.util.Log
import org.json.JSONObject

fun parseMealCategoriesJsonResult(jsonResult: JSONObject): ArrayList<MealCategory> {
    val mealCategoriesJson = jsonResult.getJSONArray("categories")
    val mealCategoryList = ArrayList<MealCategory>()

    for ( i in 0 until mealCategoriesJson.length()){
        val mealCategoryObj = mealCategoriesJson.getJSONObject(i)
        val id = mealCategoryObj.getString("idCategory")
        val category = mealCategoryObj.getString("strCategory")
        val imgUrl = mealCategoryObj.getString("strCategoryThumb")
        val mealCategory = MealCategory(id, category, imgUrl)
        mealCategoryList.add(mealCategory)
    }
    return mealCategoryList
}

fun parseMealSelectRecipesJsonResult(jsonResult: JSONObject): ArrayList<MealSelectItem> {
    val mealSelectJSON = jsonResult.getJSONArray("meals")
    val mealSelectList = ArrayList<MealSelectItem>()

    for ( i in 0 until mealSelectJSON.length()){
        val mealCategoryObj = mealSelectJSON.getJSONObject(i)
        val mealName = mealCategoryObj.getString("strMeal")
        val mealThumbUrl = mealCategoryObj.getString("strMealThumb")
        val mealId = mealCategoryObj.getLong("idMeal")
        val mealSelectItem = MealSelectItem(mealName, mealThumbUrl, mealId)
        mealSelectList.add(mealSelectItem)
    }
    return mealSelectList
}
fun parseMealRecipeJsonResult(jsonResult: JSONObject): MealRecipe {
    val mealRecipeJSONArray = jsonResult.getJSONArray("meals")
    val mealRecipeJSON = mealRecipeJSONArray.getJSONObject(0)

    val mealId = mealRecipeJSON.getLong("idMeal")
    val mealName = mealRecipeJSON.getString("strMeal")
    val mealArea = mealRecipeJSON.getString("strArea")
    val mealInstructions = mealRecipeJSON.getString("strInstructions")
    val mealImg = mealRecipeJSON.getString("strMealThumb")


    val listOfIngredients = mutableListOf<String>()
    val ingredient1 = mealRecipeJSON.getString("strIngredient1")
    val ingredient2 = mealRecipeJSON.getString("strIngredient2")
    val ingredient3 = mealRecipeJSON.getString("strIngredient3")
    val ingredient4 = mealRecipeJSON.getString("strIngredient4")
    val ingredient5 = mealRecipeJSON.getString("strIngredient5")
    val ingredient6 = mealRecipeJSON.getString("strIngredient6")
    val ingredient7 = mealRecipeJSON.getString("strIngredient7")
    val ingredient8 = mealRecipeJSON.getString("strIngredient8")
    val ingredient9 = mealRecipeJSON.getString("strIngredient9")
    val ingredient10 = mealRecipeJSON.getString("strIngredient10")
    val ingredient11 = mealRecipeJSON.getString("strIngredient11")
    val ingredient12 = mealRecipeJSON.getString("strIngredient12")
    val ingredient13 = mealRecipeJSON.getString("strIngredient13")
    val ingredient14 = mealRecipeJSON.getString("strIngredient14")
    val ingredient15 = mealRecipeJSON.getString("strIngredient15")
    val ingredient16 = mealRecipeJSON.getString("strIngredient16")
    val ingredient17 = mealRecipeJSON.getString("strIngredient17")
    val ingredient18 = mealRecipeJSON.getString("strIngredient18")
    val ingredient19 = mealRecipeJSON.getString("strIngredient19")
    val ingredient20 = mealRecipeJSON.getString("strIngredient20")
    listOfIngredients.add(ingredient1)
    listOfIngredients.add(ingredient2)
    listOfIngredients.add(ingredient3)
    listOfIngredients.add(ingredient4)
    listOfIngredients.add(ingredient5)
    listOfIngredients.add(ingredient6)
    listOfIngredients.add(ingredient7)
    listOfIngredients.add(ingredient8)
    listOfIngredients.add(ingredient9)
    listOfIngredients.add(ingredient10)
    listOfIngredients.add(ingredient11)
    listOfIngredients.add(ingredient12)
    listOfIngredients.add(ingredient13)
    listOfIngredients.add(ingredient14)
    listOfIngredients.add(ingredient15)
    listOfIngredients.add(ingredient16)
    listOfIngredients.add(ingredient17)
    listOfIngredients.add(ingredient18)
    listOfIngredients.add(ingredient19)
    listOfIngredients.add(ingredient20)
    val listOfMeasures = mutableListOf<String>()
    val measure1 = mealRecipeJSON.getString("strMeasure1")
    val measure2 = mealRecipeJSON.getString("strMeasure2")
    val measure3 = mealRecipeJSON.getString("strMeasure3")
    val measure4 = mealRecipeJSON.getString("strMeasure4")
    val measure5 = mealRecipeJSON.getString("strMeasure5")
    val measure6 = mealRecipeJSON.getString("strMeasure6")
    val measure7 = mealRecipeJSON.getString("strMeasure7")
    val measure8 = mealRecipeJSON.getString("strMeasure8")
    val measure9 = mealRecipeJSON.getString("strMeasure9")
    val measure10 = mealRecipeJSON.getString("strMeasure10")
    val measure11 = mealRecipeJSON.getString("strMeasure11")
    val measure12 = mealRecipeJSON.getString("strMeasure12")
    val measure13 = mealRecipeJSON.getString("strMeasure13")
    val measure14 = mealRecipeJSON.getString("strMeasure14")
    val measure15 = mealRecipeJSON.getString("strMeasure15")
    val measure16 = mealRecipeJSON.getString("strMeasure16")
    val measure17 = mealRecipeJSON.getString("strMeasure17")
    val measure18 = mealRecipeJSON.getString("strMeasure18")
    val measure19 = mealRecipeJSON.getString("strMeasure19")
    val measure20 = mealRecipeJSON.getString("strMeasure20")
    listOfMeasures.add(measure1)
    listOfMeasures.add(measure2)
    listOfMeasures.add(measure3)
    listOfMeasures.add(measure4)
    listOfMeasures.add(measure5)
    listOfMeasures.add(measure6)
    listOfMeasures.add(measure7)
    listOfMeasures.add(measure8)
    listOfMeasures.add(measure9)
    listOfMeasures.add(measure10)
    listOfMeasures.add(measure11)
    listOfMeasures.add(measure12)
    listOfMeasures.add(measure13)
    listOfMeasures.add(measure14)
    listOfMeasures.add(measure15)
    listOfMeasures.add(measure16)
    listOfMeasures.add(measure17)
    listOfMeasures.add(measure18)
    listOfMeasures.add(measure19)
    listOfMeasures.add(measure20)


    return MealRecipe(mealId,
        mealName,
        mealArea,
        mealInstructions,
        mealImg,
        listOfIngredients,
            listOfMeasures
    )
}