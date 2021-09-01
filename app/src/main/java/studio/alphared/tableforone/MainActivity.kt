package studio.alphared.tableforone

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.snackbar.Snackbar
import studio.alphared.tableforone.databinding.ActivityMainBinding


const val BASE_URL = "https://www.themealdb.com/"

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        isInternetConnected()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfig = AppBarConfiguration(setOf(R.id.mealListFragment))
        setupActionBarWithNavController(navController, appBarConfig)
        NavigationUI.setupActionBarWithNavController(this, navController)

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

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }
    companion object{
        const val TAG = "MainActivity"
    }


}