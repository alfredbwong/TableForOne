package studio.alphared.tableforone.meal

import android.content.Context
import androidx.lifecycle.*
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import studio.alphared.tableforone.BASE_URL
import studio.alphared.tableforone.MealReminderRepository
import studio.alphared.tableforone.meal.category.MealCategory
import studio.alphared.tableforone.meal.database.MealCategoryDatabase
import studio.alphared.tableforone.meal.recipe.MealRecipe
import studio.alphared.tableforone.meal.recipe.MealRecipeDatabase
import studio.alphared.tableforone.meal.reminder.MealReminder
import studio.alphared.tableforone.meal.reminder.MealReminderDatabase
import studio.alphared.tableforone.meal.select.MealCategoryItem
import studio.alphared.tableforone.meal.select.MealCategoryItemDatabase
import studio.alphared.tableforone.network.MealApiService
import studio.alphared.tableforone.network.Resource
import studio.alphared.tableforone.utils.TimeDateComparator
import java.util.*

class MealReminderAddViewModel(applicationContext: Context) : ViewModel() {


    var mealRecipeDetails: MealRecipe? = null
    private val repository: MealReminderRepository =
        MealReminderRepository(
            Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(MealApiService::class.java),
            MealCategoryDatabase.getInstance(applicationContext)
                .mealCategoryDao(),
            MealCategoryItemDatabase.getInstance(applicationContext)
                .mealSelectDao(),
            MealRecipeDatabase.getInstance(applicationContext)
                .mealRecipeDao(),
            MealReminderDatabase.getInstance(applicationContext)
                .mealReminderDao(),
            viewModelScope
        )

    var mealCategorySelected = MutableLiveData<String>()
    var mealRecipeItemSelected = MutableLiveData<Long>()

    val mealCategories: MediatorLiveData<Resource<List<MealCategory>>> = MediatorLiveData()

    val mealCategoryItems: MediatorLiveData<Resource<List<MealCategoryItem>>> = MediatorLiveData()

    val mealRecipeItem: MediatorLiveData<Resource<MealRecipe>> = MediatorLiveData()

    val mealRecipeItemInstructions: MediatorLiveData<List<String>> = MediatorLiveData()
    val mealRecipeItemIngredients: MediatorLiveData<List<Ingredient>> = MediatorLiveData()


    val mealReminders: MediatorLiveData<Resource<List<MealReminder>>> = MediatorLiveData()

    val mealReminder: MediatorLiveData<Resource<MealReminder>> = MediatorLiveData()

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
        myMonth = month + 1
        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR_OF_DAY)
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

    fun saveMealReminder(): Long {
        //Save to Room DB and setup broadcast Receiver
        return repository.addMealReminder(getMealReminderToBeSaved())

    }


    fun getMealCategoriesData() {
        val response = repository.getMealCategoriesFeed()
        mealCategories.addSource(response) { newData ->
            if (mealCategories.value != newData) {
                mealCategories.value = newData
            }
        }
    }

    fun getMealCategoryItemsData() {
        val response = mealCategorySelected.value?.let { category ->

            repository.getMealSelectItemFeed(category)
        }

        if (response != null) {
            mealCategoryItems.addSource(response) { newData ->
                if (mealCategoryItems.value != newData) {
                    mealCategoryItems.value = newData
                }
            }
        }

    }

    fun getMealRecipeDetailsData() {

        val response = mealRecipeItemSelected.value?.let {
            repository.getMealRecipeDetailsFeed(it)
        }
        if (response != null) {
            mealRecipeItem.addSource(response) { newData ->
                if (mealRecipeItem.value != newData) {
                    mealRecipeItem.value = newData
                }
            }
        }

    }

    fun getMealRemindersData() {

        val response = repository.getMealRemindersFeed()

        mealReminders.addSource(response) { newData ->
            if (mealReminders.value != newData) {
                //Sort the newData by chronological time
                Collections.sort(newData.data, TimeDateComparator())
                mealReminders.value = newData
            }
        }

    }

    fun getMealReminderById(mealId: Long) {
        val response = repository.getMealReminderById(mealId)

        mealReminder.addSource(response) { newData ->
            if (mealReminder.value != newData) {
                mealReminder.value = newData
            }
        }
        
    }

    fun getMealReminderToBeSaved(): MealReminder {
        return MealReminder(
            0,
            mealRecipeDetails!!.idMeal,
            mealRecipeDetails!!.strMeal,
            myYear,
            myMonth,
            myDay,
            myHour,
            myMinute,
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
            mealRecipeDetails!!.strMeasure20
        )
    }

    fun resetDateAndTime() {
        myYear = 0
        myDay = 0
        myHour = 0
        myMinute = 0
        myMonth = 0
    }

    fun convertListOfInstructions(strInstructions: String) {
        mealRecipeItemInstructions.value = strInstructions.split("\r\n").filter { instruction ->
            instruction.isNotBlank()
        }
    }

    fun createListOfIngredients(data: MealRecipe) {
        val listIngredient = mutableListOf<Ingredient>()
        val ingredient1 = Ingredient(data.strMeasure1, data.strIngredient1)
        val ingredient2 = Ingredient(data.strMeasure2, data.strIngredient2)
        val ingredient3 = Ingredient(data.strMeasure3, data.strIngredient3)
        val ingredient4 = Ingredient(data.strMeasure4, data.strIngredient4)
        val ingredient5 = Ingredient(data.strMeasure5, data.strIngredient5)
        val ingredient6 = Ingredient(data.strMeasure6, data.strIngredient6)
        val ingredient7 = Ingredient(data.strMeasure7, data.strIngredient7)
        val ingredient8 = Ingredient(data.strMeasure8, data.strIngredient8)
        val ingredient9 = Ingredient(data.strMeasure9, data.strIngredient9)
        val ingredient10 = Ingredient(data.strMeasure10, data.strIngredient10)
        val ingredient11 = Ingredient(data.strMeasure11, data.strIngredient11)
        val ingredient12 = Ingredient(data.strMeasure12, data.strIngredient12)
        val ingredient13 = Ingredient(data.strMeasure13, data.strIngredient13)
        val ingredient14 = Ingredient(data.strMeasure14, data.strIngredient14)
        val ingredient15 = Ingredient(data.strMeasure15, data.strIngredient15)
        val ingredient16 = Ingredient(data.strMeasure16, data.strIngredient16)
        val ingredient17 = Ingredient(data.strMeasure17, data.strIngredient17)
        val ingredient18 = Ingredient(data.strMeasure18, data.strIngredient18)
        val ingredient19 = Ingredient(data.strMeasure19, data.strIngredient19)
        val ingredient20 = Ingredient(data.strMeasure20, data.strIngredient20)
        val filteredList = listIngredient.apply {
            add(ingredient1)
            add(ingredient2)
            add(ingredient3)
            add(ingredient4)
            add(ingredient5)
            add(ingredient6)
            add(ingredient7)
            add(ingredient8)
            add(ingredient9)
            add(ingredient10)
            add(ingredient11)
            add(ingredient12)
            add(ingredient13)
            add(ingredient14)
            add(ingredient15)
            add(ingredient16)
            add(ingredient17)
            add(ingredient18)
            add(ingredient19)
            add(ingredient20)
        }.filter { ingredient ->
            ingredient.measure.isNotBlank() && ingredient.ingredient.isNotBlank() && ingredient.ingredient != "null" && ingredient.measure != "null"
        }
        mealRecipeItemIngredients.value = filteredList
    }

    fun createListOfIngredients(data: MealReminder) {
        val listIngredient = mutableListOf<Ingredient>()
        val ingredient1 = Ingredient(data.strMeasure1, data.strIngredient1)
        val ingredient2 = Ingredient(data.strMeasure2, data.strIngredient2)
        val ingredient3 = Ingredient(data.strMeasure3, data.strIngredient3)
        val ingredient4 = Ingredient(data.strMeasure4, data.strIngredient4)
        val ingredient5 = Ingredient(data.strMeasure5, data.strIngredient5)
        val ingredient6 = Ingredient(data.strMeasure6, data.strIngredient6)
        val ingredient7 = Ingredient(data.strMeasure7, data.strIngredient7)
        val ingredient8 = Ingredient(data.strMeasure8, data.strIngredient8)
        val ingredient9 = Ingredient(data.strMeasure9, data.strIngredient9)
        val ingredient10 = Ingredient(data.strMeasure10, data.strIngredient10)
        val ingredient11 = Ingredient(data.strMeasure11, data.strIngredient11)
        val ingredient12 = Ingredient(data.strMeasure12, data.strIngredient12)
        val ingredient13 = Ingredient(data.strMeasure13, data.strIngredient13)
        val ingredient14 = Ingredient(data.strMeasure14, data.strIngredient14)
        val ingredient15 = Ingredient(data.strMeasure15, data.strIngredient15)
        val ingredient16 = Ingredient(data.strMeasure16, data.strIngredient16)
        val ingredient17 = Ingredient(data.strMeasure17, data.strIngredient17)
        val ingredient18 = Ingredient(data.strMeasure18, data.strIngredient18)
        val ingredient19 = Ingredient(data.strMeasure19, data.strIngredient19)
        val ingredient20 = Ingredient(data.strMeasure20, data.strIngredient20)
        val filteredList = listIngredient.apply {
            add(ingredient1)
            add(ingredient2)
            add(ingredient3)
            add(ingredient4)
            add(ingredient5)
            add(ingredient6)
            add(ingredient7)
            add(ingredient8)
            add(ingredient9)
            add(ingredient10)
            add(ingredient11)
            add(ingredient12)
            add(ingredient13)
            add(ingredient14)
            add(ingredient15)
            add(ingredient16)
            add(ingredient17)
            add(ingredient18)
            add(ingredient19)
            add(ingredient20)
        }.filter { ingredient ->
            ingredient.measure.isNotBlank() && ingredient.ingredient.isNotBlank() && ingredient.ingredient != "null" && ingredient.measure != "null"
        }
        mealRecipeItemIngredients.value = filteredList
    }



    companion object {
        const val TAG = "ViewModel"
    }
}

class MealCategorySelectViewModelFactory(private val applicationContext: Context?) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = applicationContext?.let {
        MealReminderAddViewModel(
            it
        )
    } as T
}