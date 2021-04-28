package android.example.tableforone.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


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

    @GET("api/json/v1/1/filter.php")
    fun getMealCategoryItems(@Query("c")c:String): Call<String>

    @GET("api/json/v1/1/lookup.php?")
    fun getMealRecipeById(@Query("i") i: Long): Call<String>
}


/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object MealApi {
    val retrofitService: MealApiService by lazy {
        retrofit.create(MealApiService::class.java)
    }
}