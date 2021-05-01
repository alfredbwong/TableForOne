package android.example.tableforone.utils

import android.example.tableforone.meal.recipe.MealRecipe
import android.example.tableforone.meal.reminder.MealReminder
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

@BindingAdapter("ingredient")
fun bindIngredient( view: TextView, recipe:MealRecipe?) {
    recipe?.let{
        val listIngredients = convertRawMealRecipeIngredients(recipe)
        val listMeasures = convertRawMealRecipeMeasurements(recipe)

        val sb : StringBuilder = StringBuilder()
        for (i in listIngredients.indices){
            sb.append(listMeasures[i]).append(" ").append(listIngredients[i]).append("\n")
        }
        view.text = sb.toString()
    }
}

@BindingAdapter("ingredientsReminder")
fun bindIngredient( view: TextView, recipe: MealReminder?) {
    recipe?.let{
        val listIngredients = convertRawMealReminderIngredients(recipe)
        val listMeasures = convertRawMealReminderMeasurements(recipe)

        val sb : StringBuilder = StringBuilder()
        for (i in listIngredients.indices){
            sb.append(listMeasures[i]).append(" ").append(listIngredients[i]).append("\n")
        }
        view.text = sb.toString()
    }
}

