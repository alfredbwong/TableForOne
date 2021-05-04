package android.example.tableforone.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.example.tableforone.MainActivity
import android.example.tableforone.MealReminderDetailActivity
import android.example.tableforone.R
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat

const val MEAL_REMINDER_KEY_ID = "MEAL_REMINDER_KEY"
private val NOTIFICATION_ID = 0
fun NotificationManager.setupNotificationAlarm(messageBody : String, applicationContext: Context, mealReminderId: Long) {
    val intent = Intent(applicationContext, MealReminderDetailActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        putExtra(MEAL_REMINDER_KEY_ID, mealReminderId)
    }

    val pendingIntent: PendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    //Picture
    val mealImage = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.meal_icon
    )

    val bigPicStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(mealImage)
            .bigLargeIcon(null)

    val builder = NotificationCompat.Builder(applicationContext, applicationContext.getString(R.string.meal_reminder_notification_channel_id))
            .setSmallIcon(R.drawable.baseline_restaurant_24)
            .setContentTitle(applicationContext.getString(R.string.app_name))
            .setContentText(messageBody)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(bigPicStyle)
            .setLargeIcon(mealImage)
    notify(NOTIFICATION_ID, builder.build())
}
