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
        val listIngredients = listOfNotNull(recipe.strIngredient1
                ,recipe.strIngredient2
                ,recipe.strIngredient3
                ,recipe.strIngredient4
                ,recipe.strIngredient5
                ,recipe.strIngredient6
                ,recipe.strIngredient7
                ,recipe.strIngredient8
                ,recipe.strIngredient9
                ,recipe.strIngredient10
                ,recipe.strIngredient11
                ,recipe.strIngredient12
                ,recipe.strIngredient13
                ,recipe.strIngredient14
                ,recipe.strIngredient15
                ,recipe.strIngredient16
                ,recipe.strIngredient17
                ,recipe.strIngredient18
                ,recipe.strIngredient19
                ,recipe.strIngredient20)
        val listMeasures = listOfNotNull(recipe.strMeasure1
                ,recipe.strMeasure2
                ,recipe.strMeasure3
                ,recipe.strMeasure4
                ,recipe.strMeasure5
                ,recipe.strMeasure6
                ,recipe.strMeasure7
                ,recipe.strMeasure8
                ,recipe.strMeasure9
                ,recipe.strMeasure10
                ,recipe.strMeasure11
                ,recipe.strMeasure12
                ,recipe.strMeasure13
                ,recipe.strMeasure14
                ,recipe.strMeasure15
                ,recipe.strMeasure16
                ,recipe.strMeasure17
                ,recipe.strMeasure18
                ,recipe.strMeasure19
                ,recipe.strMeasure20)


        val sb : StringBuilder = StringBuilder()
        for (i in listIngredients.indices){
            if (listIngredients[i].isNotBlank() && listMeasures[i].isNotBlank() && listIngredients[i] != "null" && listMeasures[i] != "null"){
                sb.append(listIngredients[i]).append(" ").append(listMeasures[i]).append("\n")

            }
        }
        view.text = sb.toString()
    }
}

