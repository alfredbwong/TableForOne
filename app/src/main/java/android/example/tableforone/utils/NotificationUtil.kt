package android.example.tableforone.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.example.tableforone.MainActivity
import android.example.tableforone.R
import androidx.core.app.NotificationCompat

private val NOTIFICATION_ID = 0
fun NotificationManager.setupNotificationAlarm(messageBody : String, applicationContext: Context) {
    val intent = Intent(applicationContext, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    val pendingIntent: PendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    val builder = NotificationCompat.Builder(applicationContext, applicationContext.getString(R.string.meal_reminder_notification_channel_id))
            .setSmallIcon(R.drawable.meal_icon)
            .setContentTitle("My notification")
            .setContentText(messageBody)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    notify(NOTIFICATION_ID, builder.build())
}
