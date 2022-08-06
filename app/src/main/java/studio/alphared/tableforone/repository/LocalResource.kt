package studio.alphared.tableforone.repository

import studio.alphared.tableforone.network.Resource
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class LocalResource<T> (private val viewModelScope: CoroutineScope){

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

    private fun launch() {
        viewModelScope.launch {
            val diskSource =  withContext(Dispatchers.IO) {loadFromDisk()}
            result.addSource(diskSource) { data ->
                setValue(Resource.success(data))
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