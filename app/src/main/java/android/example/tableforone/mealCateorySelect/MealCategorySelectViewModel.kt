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
import java.util.*

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

    private var _showDatePicker = MutableLiveData<Boolean>()

    val showDatePicker: LiveData<Boolean>
        get() = _showDatePicker

    private var _showTimePicker = MutableLiveData<Boolean>()

    val showTimePicker: LiveData<Boolean>
        get() = _showTimePicker

    var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: Int = 0
    var myMinute: Int = 0

    init {
        getMealCategoriesResponse()
        _showDatePicker.value = false
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

    fun setupDatePicker() {
        val calendar: Calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        _showDatePicker.value = true
        _showTimePicker.value = false
    }

    fun onDateSetFun(year: Int, month: Int, dayOfMonth: Int) {
        myDay = dayOfMonth
        myYear = year
        myMonth = month
        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        _showDatePicker.value = false
        _showTimePicker.value = true
    }

    fun onTimeSetFun(hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
        _showDatePicker.value = false
        _showTimePicker.value = false
    }

}