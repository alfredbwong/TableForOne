package android.example.tableforone.mealCateorySelect

import android.example.tableforone.meal.select.MealSelectItem
import android.example.tableforone.network.MealApi
import android.example.tableforone.utils.parseMealCategoriesJsonResult
import android.example.tableforone.utils.parseMealSelectRecipesJsonResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealCategorySelectViewModel : ViewModel() {

    var _mealCategorySelected = MutableLiveData<String>()

    val mealCategorySelected: LiveData<String>
        get() = _mealCategorySelected

    private var _mealCategories = MutableLiveData<List<MealCategory>>()

    val mealCategories: LiveData<List<MealCategory>>
        get() = _mealCategories

    private var _mealItems = MutableLiveData<List<MealSelectItem>>()

    val mealItems: LiveData<List<MealSelectItem>>
        get() = _mealItems

    init {
        getMealCategoriesResponse()
    }

    private fun getMealCategoriesResponse() {
        MealApi.retrofitService.getMealCategories().enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                _mealCategories.value = mutableListOf()
            }


            override fun onResponse(call: Call<String>, response: Response<String>) {
                _mealCategories.value = parseMealCategoriesJsonResult(JSONObject(response.body()))

            }
        })
    }

    fun getMealCategoryItemsResponse() {
        _mealCategorySelected.value?.let {
            MealApi.retrofitService.getMealCategoryItems(it).enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    _mealItems.value = mutableListOf()
                }


                override fun onResponse(call: Call<String>, response: Response<String>) {
                    _mealItems.value = parseMealSelectRecipesJsonResult(JSONObject(response.body()))

                }
            })
        }
    }

}