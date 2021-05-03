package android.example.tableforone.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.example.tableforone.R
import android.example.tableforone.utils.setupNotificationAlarm
import androidx.core.content.ContextCompat

class MealReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = ContextCompat.getSystemService(context, NotificationManager::class.java) as NotificationManager

        notificationManager.setupNotificationAlarm(context.getString(R.string.app_name), context)
    }
}