package studio.alphared.tableforone.views.mealcategoryitemselect

import android.app.Application
import androidx.lifecycle.*
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import studio.alphared.tableforone.BASE_URL
import studio.alphared.tableforone.repository.MealReminderRepository
import studio.alphared.tableforone.objects.category.MealCategoryDatabase
import studio.alphared.tableforone.objects.recipe.MealRecipeDatabase
import studio.alphared.tableforone.objects.reminder.MealReminderDatabase
import studio.alphared.tableforone.objects.select.MealCategoryItem
import studio.alphared.tableforone.objects.select.MealCategoryItemDatabase
import studio.alphared.tableforone.network.MealApiService
import studio.alphared.tableforone.network.Resource

class MealCategoryItemSelectViewModel(application: Application, private val categoryId: String): AndroidViewModel(application) {
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

    val mealCategoryItems: MediatorLiveData<Resource<List<MealCategoryItem>>> = MediatorLiveData()

    init{
        getMealCategoryItemsData()
    }
    private fun getMealCategoryItemsData() {
        val response = categoryId?.let { category ->

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
}

class MealSelectCategoryItemViewModelFactory(private val application: Application?, private val categoryId : String) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = application?.let {
        MealCategoryItemSelectViewModel(
            it, categoryId
        )
    } as T
}