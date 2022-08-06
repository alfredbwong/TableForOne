package studio.alphared.tableforone.views.mealrecipedetail

import android.app.Application
import androidx.lifecycle.*
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import studio.alphared.tableforone.BASE_URL
import studio.alphared.tableforone.repository.MealReminderRepository
import studio.alphared.tableforone.objects.Ingredient
import studio.alphared.tableforone.objects.category.MealCategoryDatabase
import studio.alphared.tableforone.objects.recipe.MealRecipe
import studio.alphared.tableforone.objects.recipe.MealRecipeDatabase
import studio.alphared.tableforone.objects.reminder.MealReminderDatabase
import studio.alphared.tableforone.objects.select.MealCategoryItemDatabase
import studio.alphared.tableforone.network.MealApiService
import studio.alphared.tableforone.network.Resource

class MealRecipeDetailViewModel(application: Application, private val mealId: Long) : AndroidViewModel(application) {

    private val repository: MealReminderRepository =
        MealReminderRepository(
            Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(MealApiService::class.java),
            MealCategoryDatabase.getInstance(application)
                .mealCategoryDao(),
            MealCategoryItemDatabase.getInstance(application)
                .mealSelectDao(),
            MealRecipeDatabase.getInstance(application)
                .mealRecipeDao(),
            MealReminderDatabase.getInstance(application)
                .mealReminderDao(),
            viewModelScope
        )
    val mealRecipeItem: MediatorLiveData<Resource<MealRecipe>> = MediatorLiveData()
    private val _mealRecipeDetails = MutableLiveData<MealRecipe>()
    val mealRecipeDetails : LiveData<MealRecipe>
        get() = _mealRecipeDetails
    private val _mealRecipeItemInstructions = MutableLiveData<List<String>>()
    val mealRecipeItemInstructions : LiveData<List<String>>
        get() = _mealRecipeItemInstructions
    private val _mealRecipeItemIngredients = MutableLiveData<List<Ingredient>>()
    val mealRecipeItemIngredients : LiveData<List<Ingredient>>
        get() = _mealRecipeItemIngredients
    init{
        getMealRecipeDetailsData()

    }

    private fun getMealRecipeDetailsData() {

        val response = mealId?.let {
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
        _mealRecipeItemIngredients.value = filteredList
    }
}

class MealRecipeDetailViewModelFactory(private val application: Application?, private val mealId: Long) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = application?.let {
        MealRecipeDetailViewModel(
            it, mealId
        )
    } as T
}