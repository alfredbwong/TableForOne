package android.alphared.tableforone

import android.alphared.tableforone.meal.category.MealCategory
import android.alphared.tableforone.meal.database.MealCategoryDAO
import android.alphared.tableforone.meal.recipe.MealRecipe
import android.alphared.tableforone.meal.recipe.MealRecipeDAO
import android.alphared.tableforone.meal.reminder.MealReminder
import android.alphared.tableforone.meal.reminder.MealReminderDAO
import android.alphared.tableforone.meal.select.MealCategoryItem
import android.alphared.tableforone.meal.select.MealCategoryItemDAO
import android.alphared.tableforone.network.*
import android.alphared.tableforone.utils.parseMealCategoriesJsonResult
import android.alphared.tableforone.utils.parseMealRecipeJsonResult
import android.alphared.tableforone.utils.parseMealSelectRecipesJsonResult
import android.alphared.tableforone.utils.safeExecute
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class MealReminderRepository(private val mealService: MealApiService,
                             private val mealCategoryDAO: MealCategoryDAO,
                             private val mealCategoryItemDao: MealCategoryItemDAO,
                             private val mealRecipeDao : MealRecipeDAO,
                             private val mealReminderDao : MealReminderDAO,
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

    fun getMealSelectItemFeed(category : String): LiveData<Resource<List<MealCategoryItem>>> {
        return object : NetworkResource<List<MealCategoryItem>, String>(viewModelScope) {
            override suspend fun loadFromDisk(): LiveData<List<MealCategoryItem>> {
                return MutableLiveData(mealCategoryItemDao.getMealSelectItem(category))
            }

            override fun shouldFetch(diskResponse: List<MealCategoryItem>?): Boolean {
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

            override fun processResponse(response: String): List<MealCategoryItem> {

                val json = JSONObject(response)
                return parseMealSelectRecipesJsonResult(json, category)
            }

            override suspend fun saveToDisk(data: List<MealCategoryItem>): Boolean {

                val ids = mealCategoryItemDao.updateData(category, data)
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
                return mealRecipeDao.updateData(data) > 0
            }


        }.asLiveData()
    }

    fun getMealRemindersFeed() : LiveData<Resource<List<MealReminder>>> {
        return object : LocalResource<List<MealReminder>>(viewModelScope){
            override suspend fun loadFromDisk(): LiveData<List<MealReminder>> {
                return MutableLiveData(mealReminderDao.getMealReminders())
            }
        }.asLiveData()
    }

    fun addMealReminder(mealReminder: MealReminder) : Long {
        return mealReminderDao.insert(mealReminder)
    }

    fun getMealReminderById(mealId: Long): LiveData<Resource<MealReminder>> {
        return object : LocalResource<MealReminder>(viewModelScope) {

            override suspend fun loadFromDisk(): LiveData<MealReminder> {
                return MutableLiveData(mealReminderDao.getMealReminderById(mealId))
            }

        }.asLiveData()
    }
    fun deleteMealReminder(mealId: Long)  {
        viewModelScope.launch(Dispatchers.IO){
            mealReminderDao.deleteMealReminderById(mealId)

        }
    }
    companion object{
        const val TAG = "Repository"
    }
}