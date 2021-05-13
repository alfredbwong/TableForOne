package android.example.tableforone

import android.content.Context
import android.example.tableforone.databinding.ActivityMainBinding
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar


const val BASE_URL = "https://www.themealdb.com/"

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        isInternetConnected()

    }

    private fun turnOnInternetPermission() {
        Snackbar.make(
                binding.root,
                R.string.internet_required_error, Snackbar.LENGTH_INDEFINITE
        ).setAction(android.R.string.ok) {
            isInternetConnected()
        }.show()
    }

    private fun isInternetConnected()  {
        var isInternetConnected = false
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    isInternetConnected = hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                }
            }
        }
        if (!isInternetConnected){
            turnOnInternetPermission()
        }
    }
    companion object{
        const val TAG = "MainActivity"
    }


}