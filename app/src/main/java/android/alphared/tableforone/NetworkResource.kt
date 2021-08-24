package android.alphared.tableforone

import android.alphared.tableforone.network.Failure
import android.alphared.tableforone.network.Resource
import android.alphared.tableforone.network.Response
import android.alphared.tableforone.network.Success
import android.alphared.tableforone.utils.LogUtils
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.*

/**
 * A generic resource [T] that can provide a resource backed by both the disk and the network.
 * Network source may have a different response [K] processed into [T].
 *
 * Follows a step by step logic to fetch from disk or network source.
 * See https://developer.android.com/topic/libraries/architecture/images/network-bound-resource.png
 * Uses Co-routines to handle asynchronous tasks.
 * @param viewModelScope [CoroutineScope]
 */
abstract class NetworkResource<T, K>(private val viewModelScope: CoroutineScope) {

    /**
     * A [MediatorLiveData] resource.
     */
    private val result = MediatorLiveData<Resource<T>>()

    // Launch the step by step logic on class instantiation,
    init {
        launch()
    }

    @WorkerThread
    abstract suspend fun loadFromDisk(): LiveData<T>

    @MainThread
    abstract fun shouldFetch(diskResponse: T?): Boolean

    @WorkerThread
    abstract suspend fun fetchData(): Response<K>

    @MainThread
    abstract fun processResponse(response: K): T

    @WorkerThread
    abstract suspend fun saveToDisk(data: T): Boolean

    private fun launch() {
        viewModelScope.launch {
            val diskSource =  withContext(Dispatchers.IO) {loadFromDisk()}

            if (shouldFetch(diskSource.value)) {
                LogUtils.debug("Disk data is invalid. Fetching from network...")

                // re-attach the disk source and dispatch a loading value,
                result.addSource(diskSource) { newData ->
                    setValue(Resource.loading(newData))
                }

                // remove the source before the fetch as disk source can't be dispatched twice
                // in case of network failure,
                result.removeSource(diskSource)

                // start network fetch,
                val fetchTask = async(Dispatchers.IO) { fetchData() }
                when (val response = fetchTask.await()) {
                    is Success -> {
                        val str = response.data.toString()
                        LogUtils.debug("Success fetching ${str.slice(0..if(str.length < 151) str.length - 1 else 150)}")
                        LogUtils.debug("Saving data to disk...")
                        // save new data to disk and dispatch fresh disk value,
                        withContext(Dispatchers.IO) {
                            saveToDisk(processResponse(response.data))
                        }

                        val diskResponse = withContext(Dispatchers.IO) { loadFromDisk() }

                        // add latest disk source and send success,
                        result.addSource(diskResponse) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }
                    is Failure -> {
                        LogUtils.debug("Error fetching data: ${response.code} ${response.message}")
                        // re-use the disk data and send the error response,
                        result.addSource(diskSource) { newData ->
                            setValue(Resource.error(response.message, newData))
                        }
                    }
                }
            } else {
                LogUtils.debug("Disk data is valid. Returning disk source data...")
                // re-use disk source and send a success value,
                result.addSource(diskSource) { data ->
                    setValue(Resource.success(data))
                }
            }
        }
    }
    @MainThread
    private fun setValue(newValue: Resource<T>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }
    fun asLiveData(): LiveData<Resource<T>> = result
}