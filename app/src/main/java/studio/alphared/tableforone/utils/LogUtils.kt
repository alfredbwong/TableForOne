package studio.alphared.tableforone.utils

import retrofit2.Call
import androidx.viewbinding.BuildConfig

/**
 * Debug only Log for different levels.
 */
object LogUtils {

    private const val LOG_TAG = "asteroid:"

    fun debug(msg: String) {
        if (BuildConfig.DEBUG){
            //Do nothing configure in subclass
        }
    }

    fun warn(msg: String) {
        if (BuildConfig.DEBUG){
            //Do nothing configure in subclass
        }
    }

    fun <T> debugRetrofitCall(call: Call<T>) {
        debug("Executing ${call.request().method()}" +
                " ${call.request().url()} with headers: " +
                "${call.request().headers()} ")
    }
}