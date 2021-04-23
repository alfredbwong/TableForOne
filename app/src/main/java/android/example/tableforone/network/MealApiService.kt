package android.example.tableforone.network

import android.example.tableforone.mealSelect.MealCategory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://www.themealdb.com/"


/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

interface MealApiService {
    @GET("api/json/v1/1/categories.php")
    fun getMealCategories(): Call<String>
}


/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object MealApi {
    val retrofitService: MealApiService by lazy {
        retrofit.create(MealApiService::class.java)
    }
}