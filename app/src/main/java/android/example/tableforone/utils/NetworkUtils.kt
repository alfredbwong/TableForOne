package android.example.tableforone.utils

import android.example.tableforone.meal.recipe.MealRecipe
import android.example.tableforone.meal.select.MealCategoryItem
import android.example.tableforone.meal.category.MealCategory
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

fun parseMealSelectRecipesJsonResult(jsonResult: JSONObject, category: String): ArrayList<MealCategoryItem> {
    val mealSelectJSON = jsonResult.getJSONArray("meals")
    val mealSelectList = ArrayList<MealCategoryItem>()

    for ( i in 0 until mealSelectJSON.length()){
        val mealCategoryObj = mealSelectJSON.getJSONObject(i)
        val mealName = mealCategoryObj.getString("strMeal")
        val mealThumbUrl = mealCategoryObj.getString("strMealThumb")
        val mealId = mealCategoryObj.getLong("idMeal")
        val mealSelectItem = MealCategoryItem(0,mealName, mealThumbUrl, mealId,category)
        mealSelectList.add(mealSelectItem)
    }
    Log.i("parseMealSelect", "Size : ${mealSelectList.size}")
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


    return MealRecipe(mealId,
        mealName,
        mealArea,
        mealInstructions,
        mealImg,
        ingredient1,
        ingredient2,
        ingredient3,
        ingredient4,
        ingredient5,
        ingredient6,
        ingredient7,
        ingredient8,
        ingredient9,
        ingredient10,
        ingredient11,
        ingredient12,
        ingredient13,
        ingredient14,
        ingredient15,
        ingredient16,
        ingredient17,
        ingredient18,
        ingredient19,
        ingredient20,
        measure1,
        measure2,
        measure3,
        measure4,
        measure5,
        measure6,
        measure7,
        measure8,
        measure9,
        measure10,
        measure11,
        measure12,
        measure13,
        measure14,
        measure15,
        measure16,
        measure17,
        measure18,
        measure19,
        measure20
    )

}