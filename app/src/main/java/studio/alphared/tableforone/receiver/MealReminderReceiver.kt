package studio.alphared.tableforone.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import studio.alphared.tableforone.utils.MEAL_REMINDER_KEY_ID
import studio.alphared.tableforone.utils.MEAL_REMINDER_NAME
import studio.alphared.tableforone.utils.setupNotificationAlarm
import androidx.core.content.ContextCompat

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