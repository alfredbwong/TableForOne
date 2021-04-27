package android.example.tableforone.utils

import android.example.tableforone.meal.select.MealSelectItem
import android.example.tableforone.mealCateorySelect.MealCategory
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