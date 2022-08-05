package studio.alphared.tableforone.views.mealreminderlist

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import studio.alphared.tableforone.BASE_URL
import studio.alphared.tableforone.MealReminderRepository
import studio.alphared.tableforone.meal.MealReminderAddViewModel
import studio.alphared.tableforone.meal.database.MealCategoryDatabase
import studio.alphared.tableforone.meal.recipe.MealRecipeDatabase
import studio.alphared.tableforone.meal.reminder.MealReminder
import studio.alphared.tableforone.meal.reminder.MealReminderDatabase
import studio.alphared.tableforone.meal.select.MealCategoryItemDatabase
import studio.alphared.tableforone.network.MealApiService
import studio.alphared.tableforone.network.Resource
import studio.alphared.tableforone.utils.TimeDateComparator
import java.util.*

class MealListViewModel (application: Application) : AndroidViewModel(application){

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

    val mealReminders: MediatorLiveData<Resource<MutableList<MealReminder>>> = MediatorLiveData()
    private var _mealReminderItemSelected = MutableLiveData<Long>()
    val mealReminderItemSelected : LiveData<Long>
            get() = _mealReminderItemSelected
    private var _mealReminderDeleted = MutableLiveData<Boolean>()
    val mealReminderDeleted : LiveData<Boolean>
        get() = _mealReminderDeleted
    private var _mealReminderListEmpty = MutableLiveData<Boolean>()
    val mealReminderListEmpty : LiveData<Boolean>
        get() =_mealReminderListEmpty
    init{
        getMealRemindersData()
    }
    private fun getMealRemindersData() {

        val response = repository.getMealRemindersFeed()

        mealReminders.addSource(response) { newData ->
            if (mealReminders.value != newData) {
                //Sort the newData by chronological time
                Collections.sort(newData.data, TimeDateComparator())
                mealReminders.value = newData
            }
        }

    }

    fun moveToReminderItemDetails(id: Long) {
        _mealReminderItemSelected.value = id
    }

    fun deleteMealReminder(mealReminderId: Long) {
        repository.deleteMealReminder(mealReminderId)
        mealReminders.value?.data?.removeIf {
            mealReminder ->
            mealReminder.id == mealReminderId
        }
        checkMealReminderListSize()
        _mealReminderDeleted.value = true
    }

    private fun checkMealReminderListSize() {
        _mealReminderListEmpty.value = mealReminders.value?.data?.size == (0 ?: true)
    }

    fun deletionAndUpdateComplete() {
        _mealReminderDeleted.value = false
    }

}
class MealReminderListViewModelFactory(private val application: Application?) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = application?.let {
        MealListViewModel(
            it
        )
    } as T
}