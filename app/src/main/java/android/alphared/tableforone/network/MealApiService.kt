package android.alphared.tableforone.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MealApiService {
    @GET("api/json/v1/1/categories.php")
    fun getMealCategories(): Call<String>

    @GET("api/json/v1/1/filter.php")
    fun getMealCategoryItems(@Query("c")c:String): Call<String>

    @GET("api/json/v1/1/lookup.php?")
    fun getMealRecipeById(@Query("i") i: Long): Call<String>
}


