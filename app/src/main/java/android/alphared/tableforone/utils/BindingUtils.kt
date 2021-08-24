package android.alphared.tableforone.utils

import android.alphared.tableforone.meal.reminder.MealReminder
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
                .load(imgUri)
                .into(imgView)

    }
}

@BindingAdapter("mealReminderDateTime")
fun mealReminderTime(view: TextView, mealReminder: MealReminder?){
    mealReminder?.let{
        val year =(mealReminder.mealYear).toString()
        val month =(mealReminder.mealMonth).toString()
        val day =(mealReminder.mealDay).toString()
        val hour = (mealReminder.mealHour).toString()
        val minute = (mealReminder.mealMinute).toString()

        val sb = StringBuilder()
        sb.append("You have scheduled this meal for: ")
        sb.append(day).append("/").append(month).append("/").append(year)
        sb.append(" at ")

        if (mealReminder.mealHour < 10 ){
            sb.append("0").append(hour)
        } else {
            sb.append(hour)
        }
        sb.append(":")
        if (mealReminder.mealMinute < 10 ){
            sb.append("0").append(minute)
        } else {
            sb.append(minute)
        }
        view.text = sb.toString()
    }
}
