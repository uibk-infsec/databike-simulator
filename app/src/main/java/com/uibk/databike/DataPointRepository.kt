package com.uibk.databike

import androidx.lifecycle.LiveData
import com.uibk.databike.DataPoint
import com.uibk.databike.DataPointDao

class DataPointRepository(private val dataPointDao: DataPointDao) {
    val allDataPoints: LiveData<List<DataPoint>> = dataPointDao.getDataPoints()

    suspend fun insert(dataPoint: DataPoint) {
        dataPointDao.insert(dataPoint)
    }
}