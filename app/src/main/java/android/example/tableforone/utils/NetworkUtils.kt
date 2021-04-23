package android.example.tableforone.utils

import android.example.tableforone.mealSelect.MealCategory
import android.util.Log
import org.json.JSONObject

fun parseAsteroidsJsonResult(jsonResult: JSONObject): ArrayList<MealCategory> {
    val mealCategoriesJson = jsonResult.getJSONArray("categories")
    val mealCategoryList = ArrayList<MealCategory>()

    Log.i("NetworkUtils", mealCategoriesJson.toString())

    for ( i in 0 until mealCategoriesJson.length()){
        val mealCategoryObj = mealCategoriesJson.getJSONObject(i)
        val id = mealCategoryObj.getString("idCategory")
        val category = mealCategoryObj.getString("strCategory")
        val mealCategory = MealCategory(id, category, "")
        mealCategoryList.add(mealCategory)
    }
    return mealCategoryList
}