package android.example.tableforone

import android.example.tableforone.meal.database.MealCategoryDAO
import android.example.tableforone.meal.recipe.MealRecipe
import android.example.tableforone.meal.recipe.MealRecipeDAO
import android.example.tableforone.meal.select.MealSelectDAO
import android.example.tableforone.meal.select.MealSelectItem
import android.example.tableforone.mealCateorySelect.MealCategory
import android.example.tableforone.network.*
import android.example.tableforone.utils.parseMealCategoriesJsonResult
import android.example.tableforone.utils.parseMealRecipeJsonResult
import android.example.tableforone.utils.parseMealSelectRecipesJsonResult
import android.example.tableforone.utils.safeExecute
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import org.json.JSONObject

class MealReminderRepository(private val mealService: MealApiService,
                             private val mealCategoryDAO: MealCategoryDAO,
                             private val mealSelectDao: MealSelectDAO,
                             private val mealRecipeDao : MealRecipeDAO,
                             private val viewModelScope: CoroutineScope) {

    fun getMealCategoriesFeed() : LiveData<Resource<List<MealCategory>>> {
        return object : NetworkResource<List<MealCategory>, String>(viewModelScope) {
            override suspend fun loadFromDisk(): LiveData<List<MealCategory>> {
                return MutableLiveData(mealCategoryDAO.getMealCategories())
            }

            override fun shouldFetch(diskResponse: List<MealCategory>?): Boolean {
                return diskResponse.isNullOrEmpty()
            }

            override suspend fun fetchData(): Response<String> {
                val call = mealService.getMealCategories()
                val response = call.safeExecute()

                if (!response.isSuccessful || response.body().isNullOrEmpty()) {
                    return Failure(400, "Invalid Response")
                }

                return Success(response.body() as String)
            }

            override fun processResponse(response: String): List<MealCategory> {
                val json = JSONObject(response)
                return parseMealCategoriesJsonResult(json)
            }

            override suspend fun saveToDisk(data: List<MealCategory>): Boolean {
                val ids = mealCategoryDAO.updateData(data)
                return ids.isNotEmpty()
            }

        }.asLiveData()
    }

    fun getMealSelectItemFeed(category : String): LiveData<Resource<List<MealSelectItem>>> {
        return object : NetworkResource<List<MealSelectItem>, String>(viewModelScope) {
            override suspend fun loadFromDisk(): LiveData<List<MealSelectItem>> {
                return MutableLiveData(mealSelectDao.getMealSelectItem())
            }

            override fun shouldFetch(diskResponse: List<MealSelectItem>?): Boolean {
                return diskResponse.isNullOrEmpty()
            }

            override suspend fun fetchData(): Response<String> {
                val call = mealService.getMealCategoryItems(category)
                val response = call.safeExecute()

                if (!response.isSuccessful || response.body().isNullOrEmpty()) {
                    return Failure(400, "Invalid Response")
                }

                return Success(response.body() as String)
            }

            override fun processResponse(response: String): List<MealSelectItem> {
                val json = JSONObject(response)
                return parseMealSelectRecipesJsonResult(json)
            }

            override suspend fun saveToDisk(data: List<MealSelectItem>): Boolean {
                val ids = mealSelectDao.updateData(data)
                return ids.isNotEmpty()
            }
        }.asLiveData()
    }

    fun getMealRecipeDetailsFeed(recipeId: Long): LiveData<Resource<MealRecipe>> {
        return object : NetworkResource<MealRecipe, String>(viewModelScope) {
            override suspend fun loadFromDisk(): LiveData<MealRecipe> {
                return MutableLiveData(mealRecipeDao.getMealRecipeById(recipeId))
            }

            override fun shouldFetch(diskResponse: MealRecipe?): Boolean {
                return diskResponse == null
            }

            override suspend fun fetchData(): Response<String> {
                val call = mealService.getMealRecipeById(recipeId)
                val response = call.safeExecute()
                if (!response.isSuccessful || response.body().isNullOrEmpty()) {
                    return Failure(400, "Invalid Response")
                }

                return Success(response.body() as String)

            }

            override fun processResponse(response: String): MealRecipe {
                val json = JSONObject(response)
                return parseMealRecipeJsonResult(json)
            }

            override suspend fun saveToDisk(data: MealRecipe): Boolean {
                val id = mealRecipeDao.updateData(data)
                return true
            }


        }.asLiveData()
    }
}