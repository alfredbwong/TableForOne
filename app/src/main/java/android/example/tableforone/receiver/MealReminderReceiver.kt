package android.example.tableforone.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.example.tableforone.utils.MEAL_REMINDER_KEY_ID
import android.example.tableforone.utils.MEAL_REMINDER_NAME
import android.example.tableforone.utils.setupNotificationAlarm
import androidx.core.content.ContextCompat
import androidx.room.util.StringUtil

class MealReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = ContextCompat.getSystemService(context, NotificationManager::class.java) as NotificationManager

        val mealReminderId = intent.getLongExtra(MEAL_REMINDER_KEY_ID, 0L)
        val mealName =intent.getStringExtra(MEAL_REMINDER_NAME)
        if (mealReminderId != 0L){
            notificationManager.setupNotificationAlarm(context, mealReminderId, mealName)

        }
    }
}