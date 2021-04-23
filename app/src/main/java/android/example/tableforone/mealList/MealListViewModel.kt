package android.example.tableforone.mealList

import android.example.tableforone.network.MealApi
import android.example.tableforone.utils.parseAsteroidsJsonResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealListViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()

    val response : LiveData<String>
        get() = _response

    init{
        getMealResponse()
    }

    private fun getMealResponse(){
        MealApi.retrofitService.getMealCategories().enqueue( object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }


            override fun onResponse(call: Call<String>, response: Response<String>) {
                _response.value = parseAsteroidsJsonResult(JSONObject(response.body())).toString()

            }
        })
        //_response.value = "Set the response here"
    }


}