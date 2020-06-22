package com.example.compass

import androidx.lifecycle.LiveData

class DataPointRepository(private val dataPointDao: DataPointDao) {
    val allDataPoints: LiveData<List<DataPoint>> = dataPointDao.getDataPoints()

    suspend fun insert(dataPoint: DataPoint) {
        dataPointDao.insert(dataPoint)
    }
}