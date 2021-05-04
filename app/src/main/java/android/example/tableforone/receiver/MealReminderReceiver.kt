package android.example.tableforone.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.example.tableforone.MealReminderRepository
import android.example.tableforone.R
import android.example.tableforone.utils.MEAL_REMINDER_KEY_ID
import android.example.tableforone.utils.setupNotificationAlarm
import androidx.core.content.ContextCompat
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class MealReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = ContextCompat.getSystemService(context, NotificationManager::class.java) as NotificationManager

        val mealReminderId = intent.getLongExtra(MEAL_REMINDER_KEY_ID, 0L)
        if (mealReminderId != 0L){
            notificationManager.setupNotificationAlarm(context.getString(R.string.app_name), context, mealReminderId)

        }
    }
}