package android.example.tableforone.mealCateorySelect

import android.content.Context
import android.example.tableforone.BASE_URL
import android.example.tableforone.MealReminderRepository
import android.example.tableforone.meal.database.MealCategoryDatabase
import android.example.tableforone.meal.recipe.MealRecipe
import android.example.tableforone.meal.recipe.MealRecipeDatabase
import android.example.tableforone.meal.reminder.MealReminder
import android.example.tableforone.meal.reminder.MealReminderDatabase
import android.example.tableforone.meal.select.MealSelectDatabase
import android.example.tableforone.meal.select.MealSelectItem
import android.example.tableforone.network.MealApiService
import android.example.tableforone.network.Resource
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

class MealCategorySelectViewModel(applicationContext: Context) : ViewModel() {


    var mealRecipeDetails: MealRecipe? = null
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
                    MealReminderDatabase.getInstance(applicationContext)
                            .mealReminderDao(),
                    viewModelScope
            )

    var mealCategorySelected = MutableLiveData<String>()
    var mealRecipeItemSelected = MutableLiveData<Long>()
    var mealReminderItemSelected = MutableLiveData<Long>()

    val mealCategories :MediatorLiveData<Resource<List<MealCategory>>> = MediatorLiveData()

    val mealCategoryItems :MediatorLiveData<Resource<List<MealSelectItem>>> = MediatorLiveData()

    val mealRecipeItem: MediatorLiveData<Resource<MealRecipe>> = MediatorLiveData()

    val mealReminders: MediatorLiveData<Resource<List<MealReminder>>> = MediatorLiveData()


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
        GlobalScope.launch(Dispatchers.IO){
            repository.addMealReminder(MealReminder(
                    mealRecipeDetails!!.idMeal,
                    mealRecipeDetails!!.strMeal,
                    myYear,
                    myMonth,
                    myDay,
                    myHour,
                    myDay,
                    mealRecipeDetails!!.strMealThumb,
                    mealRecipeDetails!!.strInstructions,
                    mealRecipeDetails!!.strIngredient1,
                    mealRecipeDetails!!.strIngredient2,
                    mealRecipeDetails!!.strIngredient3,
                    mealRecipeDetails!!.strIngredient4,
                    mealRecipeDetails!!.strIngredient5,
                    mealRecipeDetails!!.strIngredient6,
                    mealRecipeDetails!!.strIngredient7,
                    mealRecipeDetails!!.strIngredient8,
                    mealRecipeDetails!!.strIngredient9,
                    mealRecipeDetails!!.strIngredient10,
                    mealRecipeDetails!!.strIngredient11,
                    mealRecipeDetails!!.strIngredient12,
                    mealRecipeDetails!!.strIngredient13,
                    mealRecipeDetails!!.strIngredient14,
                    mealRecipeDetails!!.strIngredient15,
                    mealRecipeDetails!!.strIngredient16,
                    mealRecipeDetails!!.strIngredient17,
                    mealRecipeDetails!!.strIngredient18,
                    mealRecipeDetails!!.strIngredient19,
                    mealRecipeDetails!!.strIngredient20,
                    mealRecipeDetails!!.strMeasure1,
                    mealRecipeDetails!!.strMeasure2,
                    mealRecipeDetails!!.strMeasure3,
                    mealRecipeDetails!!.strMeasure4,
                    mealRecipeDetails!!.strMeasure5,
                    mealRecipeDetails!!.strMeasure6,
                    mealRecipeDetails!!.strMeasure7,
                    mealRecipeDetails!!.strMeasure8,
                    mealRecipeDetails!!.strMeasure9,
                    mealRecipeDetails!!.strMeasure10,
                    mealRecipeDetails!!.strMeasure11,
                    mealRecipeDetails!!.strMeasure12,
                    mealRecipeDetails!!.strMeasure13,
                    mealRecipeDetails!!.strMeasure14,
                    mealRecipeDetails!!.strMeasure15,
                    mealRecipeDetails!!.strMeasure16,
                    mealRecipeDetails!!.strMeasure17,
                    mealRecipeDetails!!.strMeasure18,
                    mealRecipeDetails!!.strMeasure19,
                    mealRecipeDetails!!.strMeasure20)
            )
        }


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

    fun getMealRemindersData() {

        val response =repository.getMealRemindersFeed()

        if (response != null){
            mealReminders.addSource(response){
                newData ->
                if (mealReminders.value != newData){
                    mealReminders.value = newData
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