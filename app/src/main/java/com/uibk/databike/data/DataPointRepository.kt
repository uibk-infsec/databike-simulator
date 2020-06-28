package com.uibk.databike.data

import androidx.lifecycle.LiveData
import com.uibk.databike.data.DataPoint
import com.uibk.databike.data.DataPointDao

class DataPointRepository(private val dataPointDao: DataPointDao) {
    val allDataPoints: LiveData<List<DataPoint>> = dataPointDao.getDataPoints()

    suspend fun insert(dataPoint: DataPoint) {
        dataPointDao.insert(dataPoint)
    }
}