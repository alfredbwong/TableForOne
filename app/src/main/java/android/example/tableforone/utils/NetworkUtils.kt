package android.example.tableforone.utils

import android.example.tableforone.mealCateorySelect.MealCategory
import android.util.Log
import org.json.JSONObject

fun parseAsteroidsJsonResult(jsonResult: JSONObject): ArrayList<MealCategory> {
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