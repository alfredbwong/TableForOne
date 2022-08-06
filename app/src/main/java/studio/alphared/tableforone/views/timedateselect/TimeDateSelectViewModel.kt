package studio.alphared.tableforone.views.timedateselect

import android.app.Application
import androidx.lifecycle.*
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import studio.alphared.tableforone.BASE_URL
import studio.alphared.tableforone.repository.MealReminderRepository
import studio.alphared.tableforone.objects.category.MealCategoryDatabase
import studio.alphared.tableforone.objects.recipe.MealRecipeDatabase
import studio.alphared.tableforone.objects.reminder.MealReminder
import studio.alphared.tableforone.objects.reminder.MealReminderDatabase
import studio.alphared.tableforone.objects.select.MealCategoryItemDatabase
import studio.alphared.tableforone.network.MealApiService
import java.util.*

class TimeDateSelectViewModel (application: Application): AndroidViewModel(application){
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
    fun setupDatePicker() {
        val calendar: Calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        _showDatePicker.value = true
        _showTimePicker.value = false
    }




    fun resetDateAndTime() {
        myYear = 0
        myDay = 0
        myHour = 0
        myMinute = 0
        myMonth = 0
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
    fun saveMealReminder(mealReminderSave: MealReminder?): Long? {
        //Save to Room DB and setup broadcast Receiver
        return mealReminderSave?.let { repository.addMealReminder(it) }

    }
    init {
        _showDatePicker.value = false
    }

}

class TimeDateSelectViewModelFactory(private val application: Application?) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = application?.let {
        TimeDateSelectViewModel(
            it
        )
    } as T
}