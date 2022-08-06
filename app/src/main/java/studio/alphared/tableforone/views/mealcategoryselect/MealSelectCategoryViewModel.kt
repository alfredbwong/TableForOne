package studio.alphared.tableforone.views.mealcategoryselect

import android.app.Application
import androidx.lifecycle.*
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import studio.alphared.tableforone.BASE_URL
import studio.alphared.tableforone.repository.MealReminderRepository
import studio.alphared.tableforone.objects.category.MealCategory
import studio.alphared.tableforone.objects.category.MealCategoryDatabase
import studio.alphared.tableforone.objects.recipe.MealRecipeDatabase
import studio.alphared.tableforone.objects.reminder.MealReminderDatabase
import studio.alphared.tableforone.objects.select.MealCategoryItemDatabase
import studio.alphared.tableforone.network.MealApiService
import studio.alphared.tableforone.network.Resource

class MealSelectCategoryViewModel(application: Application) : AndroidViewModel(application) {
    val mealCategories: MediatorLiveData<Resource<List<MealCategory>>> = MediatorLiveData()

    private var _mealCategorySelected = MutableLiveData<String>()
    val mealCategorySelected : LiveData<String>
        get() = _mealCategorySelected

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

    init{
        getMealCategoriesData()

    }
    private fun getMealCategoriesData() {
        val response = repository.getMealCategoriesFeed()
        mealCategories.addSource(response) { newData ->
            if (mealCategories.value != newData) {
                mealCategories.value = newData
            }
        }
    }

    fun saveMealCategorySelected(mealCategory: MealCategory) {
        _mealCategorySelected.value = mealCategory.idCategory
    }
}



class MealSelectCategoryViewModelFactory(private val application: Application?) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = application?.let {
        MealSelectCategoryViewModel(
            it
        )
    } as T
}