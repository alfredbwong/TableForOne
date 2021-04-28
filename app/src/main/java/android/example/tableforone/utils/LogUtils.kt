package android.example.tableforone.utils

import android.util.Log
import retrofit2.Call
import androidx.viewbinding.BuildConfig

/**
 * Debug only Log for different levels.
 */
object LogUtils {

    private const val LOG_TAG = "asteroid:"

    fun debug(msg: String) {
        if (BuildConfig.DEBUG)
            Log.d(LOG_TAG, msg)
    }

    fun warn(msg: String) {
        if (BuildConfig.DEBUG)
            Log.w(LOG_TAG, msg)
    }

    fun <T> debugRetrofitCall(call: Call<T>) {
        debug("Executing ${call.request().method()}" +
                " ${call.request().url()} with headers: " +
                "${call.request().headers()} ")
    }
}