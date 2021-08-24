package android.alphared.tableforone.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.alphared.tableforone.MealReminderDetailActivity
import android.alphared.tableforone.R
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat

const val MEAL_REMINDER_KEY_ID = "MEAL_REMINDER_KEY"
const val MEAL_REMINDER_NAME = "MEAL_REMINDER_NAME"
private const val NOTIFICATION_ID = 0
fun NotificationManager.setupNotificationAlarm(applicationContext: Context, mealReminderId: Long, mealName: String?) {

    val intent = Intent(applicationContext, MealReminderDetailActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        putExtra(MEAL_REMINDER_KEY_ID, mealReminderId)
    }

    val pendingIntent: PendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    //Picture
    val mealImage = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.table_for_one_icon
    )

    val bigPicStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(mealImage)
            .bigLargeIcon(null)

    val builder = NotificationCompat.Builder(applicationContext, applicationContext.getString(R.string.meal_reminder_notification_channel_id))
            .setSmallIcon(R.drawable.baseline_restaurant_24)
            .setContentTitle(applicationContext.getString(R.string.app_name))
            .setContentText(String.format(applicationContext.getString(R.string.notification_body_message, mealName)))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(bigPicStyle)
            .setLargeIcon(mealImage)
    notify(NOTIFICATION_ID, builder.build())
}
