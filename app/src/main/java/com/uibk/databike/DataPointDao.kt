package com.uibk.databike

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uibk.databike.DataPoint

@Dao
interface DataPointDao {
    @Query("SELECT * FROM data_point")
    fun getDataPoints(): LiveData<List<DataPoint>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(dataPoint: DataPoint)

    @Query("DELETE FROM data_point")
    suspend fun deleteAll()
}