package studio.alphared.tableforone

import junit.framework.Assert.assertTrue
import org.json.JSONObject
import org.junit.Test
import studio.alphared.tableforone.utils.parseMealCategoriesJsonResult
import studio.alphared.tableforone.utils.parseMealSelectRecipesJsonResult

class NetworkUtilTest {

    @Test
    fun parseMealCategoriesJsonResult_jsonToMealCategory_oneMealCategory(){
        //Given
        val json : JSONObject = JSONObject("{\"categories\":[{\"idCategory\":\"1\",\"strCategory\":\"Beef\", \"strCategoryThumb\":\"BeefThumb\"}]}")
        //When
        val listOfMealCategories = parseMealCategoriesJsonResult(json)
        //Then
        assertTrue(listOfMealCategories.size == 1)
    }
    @Test
    fun parseMealCategoriesJsonResult_jsonToMealCategory_noMealCategory(){
        //Given
        val json : JSONObject = JSONObject("{\"categories\":[]}")
        //When
        val listOfMealCategories = parseMealCategoriesJsonResult(json)
        //Then
        assertTrue(listOfMealCategories.isEmpty())
    }
    @Test
    fun parseMealSelectRecipesJsonResult_jsonToMealCategory_oneMealCategory(){
        //Given
        val json : JSONObject = JSONObject("{\"meals\":[{\"strMeal\":\"someMeal\",\"strMealThumb\":\"mealThumb\", \"idMeal\":1}]}")
        //When
        val listOfMealSelected = parseMealSelectRecipesJsonResult(json, "category_string")
        //Then
        assertTrue(listOfMealSelected.size == 1)
    }
    @Test
    fun parseMealSelectRecipesJsonResult_jsonToMealCategory_noMealCategory(){
        //Given
        val json : JSONObject = JSONObject("{\"meals\":[]}")
        //When
        val listOfMealSelected = parseMealSelectRecipesJsonResult(json,"category_string")
        //Then
        assertTrue(listOfMealSelected.isEmpty())
    }
}