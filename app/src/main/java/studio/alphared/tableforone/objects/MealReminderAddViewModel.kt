package studio.alphared.tableforone.objects

import android.content.Context
import androidx.lifecycle.*
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import studio.alphared.tableforone.BASE_URL
import studio.alphared.tableforone.repository.MealReminderRepository
import studio.alphared.tableforone.objects.category.MealCategoryDatabase
import studio.alphared.tableforone.objects.recipe.MealRecipe
import studio.alphared.tableforone.objects.recipe.MealRecipeDatabase
import studio.alphared.tableforone.objects.reminder.MealReminder
import studio.alphared.tableforone.objects.reminder.MealReminderDatabase
import studio.alphared.tableforone.objects.select.MealCategoryItemDatabase
import studio.alphared.tableforone.network.MealApiService
import studio.alphared.tableforone.network.Resource
import studio.alphared.tableforone.utils.TimeDateComparator
import java.util.*

class MealReminderAddViewModel(applicationContext: Context) : ViewModel() {


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

    private val _mealReminderToBeSaved = MutableLiveData<MealRecipe>()
    val mealReminderToBeSaved :LiveData<MealRecipe>
        get() = _mealReminderToBeSaved

    private val _mealRecipeItemInstructions = MutableLiveData<List<String>>()
    val mealRecipeItemInstructions :LiveData<List<String>>
        get() = _mealRecipeItemInstructions

    val mealRecipeItemIngredients: MediatorLiveData<List<Ingredient>> = MediatorLiveData()

    val mealReminders: MediatorLiveData<Resource<List<MealReminder>>> = MediatorLiveData()

    val mealReminder: MediatorLiveData<Resource<MealReminder>> = MediatorLiveData()


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


    fun processAndSaveListOfInstructions(strInstructions: String) {
        _mealRecipeItemInstructions.value = strInstructions.split("\r\n").filter { instruction ->
            instruction.isNotBlank()
        }
    }

    fun processAndSaveListOfIngredients(data: MealRecipe) {
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
        //mealRecipeItemIngredients.value = filteredList
    }

    fun processAndSaveListOfIngredients(data: MealReminder) {
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

    fun saveMealRecipeDetails(data: MealRecipe) {
        _mealReminderToBeSaved.value = data
    }

    fun getMealReminderToBeSaved(myYear: Int, myMonth: Int, myDay: Int, myHour: Int, myMinute: Int): MealReminder? {
        return _mealReminderToBeSaved.value?.let {
            mealRecipe ->
            MealReminder(
                0,
                mealRecipe.idMeal,
                mealRecipe!!.strMeal,
                myYear,
                myMonth,
                myDay,
                myHour,
                myMinute,
                mealRecipe.strMealThumb,
                mealRecipe.strInstructions,
                mealRecipe.strIngredient1,
                mealRecipe.strIngredient2,
                mealRecipe.strIngredient3,
                mealRecipe.strIngredient4,
                mealRecipe.strIngredient5,
                mealRecipe.strIngredient6,
                mealRecipe.strIngredient7,
                mealRecipe.strIngredient8,
                mealRecipe.strIngredient9,
                mealRecipe.strIngredient10,
                mealRecipe.strIngredient11,
                mealRecipe.strIngredient12,
                mealRecipe.strIngredient13,
                mealRecipe.strIngredient14,
                mealRecipe.strIngredient15,
                mealRecipe.strIngredient16,
                mealRecipe.strIngredient17,
                mealRecipe.strIngredient18,
                mealRecipe.strIngredient19,
                mealRecipe.strIngredient20,
                mealRecipe.strMeasure1,
                mealRecipe.strMeasure2,
                mealRecipe.strMeasure3,
                mealRecipe.strMeasure4,
                mealRecipe.strMeasure5,
                mealRecipe.strMeasure6,
                mealRecipe.strMeasure7,
                mealRecipe.strMeasure8,
                mealRecipe.strMeasure9,
                mealRecipe.strMeasure10,
                mealRecipe.strMeasure11,
                mealRecipe.strMeasure12,
                mealRecipe.strMeasure13,
                mealRecipe.strMeasure14,
                mealRecipe.strMeasure15,
                mealRecipe.strMeasure16,
                mealRecipe.strMeasure17,
                mealRecipe.strMeasure18,
                mealRecipe.strMeasure19,
                mealRecipe.strMeasure20
            )
        }
    }


    companion object {
        const val TAG = "ViewModel"
    }
}

class MealReminderAddViewModelFactory(private val applicationContext: Context?) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = applicationContext?.let {
        MealReminderAddViewModel(
            it
        )
    } as T
}