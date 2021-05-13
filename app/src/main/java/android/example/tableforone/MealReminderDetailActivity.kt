package android.example.tableforone

import android.content.Intent
import android.example.tableforone.databinding.ActivityMealReminderDetailBinding
import android.example.tableforone.meal.MealCategorySelectViewModelFactory
import android.example.tableforone.meal.MealReminderAddViewModel
import android.example.tableforone.network.Status
import android.example.tableforone.utils.MEAL_REMINDER_KEY_ID
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

class MealReminderDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMealReminderDetailBinding
    private val viewModel: MealReminderAddViewModel by viewModels {
        MealCategorySelectViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


         val mealId = intent.getLongExtra(MEAL_REMINDER_KEY_ID, 0L)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_meal_reminder_detail)

        viewModel.getMealReminderById(mealId)
        viewModel.mealReminder.observe(this, { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    if (resource.data == null) {
                        Log.i(TAG, "Missing resource data")

                    }
                    binding.mealReminderDetail = resource.data
                    binding.executePendingBindings()
                }
                Status.LOADING -> {


                }
                Status.ERROR -> {


                }
            }
        })

        binding.backReminderButton.setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
    companion object{
        const val TAG = "MealReminderActivity"
    }

}