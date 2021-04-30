package android.example.tableforone.mealCateorySelect

import android.content.Context
import android.example.tableforone.BASE_URL
import android.example.tableforone.MealReminderRepository
import android.example.tableforone.meal.database.MealCategoryDatabase
import android.example.tableforone.meal.recipe.MealRecipe
import android.example.tableforone.meal.recipe.MealRecipeDatabase
import android.example.tableforone.meal.select.MealSelectDatabase
import android.example.tableforone.meal.select.MealSelectItem
import android.example.tableforone.network.MealApiService
import android.example.tableforone.network.Resource
import androidx.lifecycle.*
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

class MealCategorySelectViewModel(applicationContext: Context) : ViewModel() {


    private val repository: MealReminderRepository =
            MealReminderRepository(
                Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build()
                    .create(MealApiService::class.java),
                MealCategoryDatabase.getInstance(applicationContext)
                    .mealCategoryDao(),
                MealSelectDatabase.getInstance(applicationContext)
                    .mealSelectDao(),
                MealRecipeDatabase.getInstance(applicationContext)
                    .mealRecipeDao(),
                viewModelScope
            )

    var mealCategorySelected = MutableLiveData<String>()
    var mealRecipeItemSelected = MutableLiveData<Long>()

    val mealCategories :MediatorLiveData<Resource<List<MealCategory>>> = MediatorLiveData()

    val mealCategoryItems :MediatorLiveData<Resource<List<MealSelectItem>>> = MediatorLiveData()

    val mealRecipeItem: MediatorLiveData<Resource<MealRecipe>> = MediatorLiveData()

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
        _showDatePicker.value = false
    }

//    fun getMealRecipeResponse(){
//        mealRecipeItemSelected.value?.let {
//            MealApi.retrofitService.getMealRecipeById(it).enqueue(object : Callback<String> {
//                override fun onFailure(call: Call<String>, t: Throwable) {
//                    Log.i("ViewModel", "Failed to get recipe")
//                    _mealRecipeItem.value = null
//                }
//
//
//                override fun onResponse(call: Call<String>, response: Response<String>) {
//                    _mealRecipeItem.value = parseMealRecipeJsonResult(JSONObject(response.body()))
//
//                }
//            })
//        }
//    }

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

    fun saveMealReminder() {
        //Save to Room DB and setup broadcast Receiver
    }

    fun getMealCategoriesData() {
        val response = repository.getMealCategoriesFeed()
        mealCategories.addSource(response){
            newData ->
            if (mealCategories.value != newData){
                mealCategories.value = newData
            }
        }
    }

    fun getMealCategoryItemsData() {

        val response = mealCategorySelected.value?.let {
            repository.getMealSelectItemFeed(it)
        }
        if (response != null){
            mealCategoryItems.addSource(response){
                newData ->
                if (mealCategoryItems.value != newData){
                    mealCategoryItems.value = newData
                }
            }
        }

    }
    fun getMealRecipeDetailsData() {

        val response = mealRecipeItemSelected.value?.let {
            repository.getMealRecipeDetailsFeed(it)
        }
        if (response != null){
            mealRecipeItem.addSource(response){
                    newData ->
                if (mealRecipeItem.value != newData){
                    mealRecipeItem.value = newData
                }
            }
        }

    }

}

class MealCategorySelectViewModelFactory(private val applicationContext: Context?): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = applicationContext?.let {
        MealCategorySelectViewModel(
                it
        )
    } as T
}