package android.example.tableforone

import android.example.tableforone.meal.database.MealCategoryDatabase
import android.example.tableforone.network.MealApiService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
const val BASE_URL = "https://www.themealdb.com/"

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}