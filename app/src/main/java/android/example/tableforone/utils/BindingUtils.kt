package android.example.tableforone.utils

import android.example.tableforone.meal.recipe.MealRecipe
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
            sb.append(listIngredients[i]).append(" ").append(listMeasures[i]).append("\n")
        }
        view.text = sb.toString()
    }
}

