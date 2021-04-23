package android.example.tableforone.mealCateorySelect

import android.example.tableforone.network.MealApi
import android.example.tableforone.utils.parseAsteroidsJsonResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealCategorySelectViewModel : ViewModel() {

    private val _mealCategories = MutableLiveData<List<MealCategory>>()

    val mealCategories : LiveData<List<MealCategory>>
        get() = _mealCategories

    init{
        getMealResponse()
    }

    private fun getMealResponse(){
        MealApi.retrofitService.getMealCategories().enqueue( object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                _mealCategories.value = mutableListOf()
            }


            override fun onResponse(call: Call<String>, response: Response<String>) {
                _mealCategories.value = parseAsteroidsJsonResult(JSONObject(response.body()))

            }
        })
    }


}