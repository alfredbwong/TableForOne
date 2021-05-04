package android.example.tableforone

import android.Manifest
import android.content.pm.PackageManager
import android.example.tableforone.meal.database.MealCategoryDatabase
import android.example.tableforone.network.MealApiService
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
const val BASE_URL = "https://www.themealdb.com/"

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPermissions()
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied")
        }
    }

    companion object{
        const val TAG = "MainActivity"
    }

}