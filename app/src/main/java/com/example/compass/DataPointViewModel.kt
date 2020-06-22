package com.example.compass

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataPointViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DataPointRepository
    val allDataPoints: LiveData<List<DataPoint>>

    init {
        val dataPointDao = DataPointRoomDatabase.getDatabase(application).dataPointDao()
        repository = DataPointRepository(dataPointDao)
        allDataPoints = repository.allDataPoints
    }

    fun insert(dataPoint: DataPoint) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(dataPoint)
    }
}