package com.uibk.databike

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "data_point")
data class DataPoint(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val absRotX: Float,
    val absRotY: Float,
    val absRotZ: Float
) {
}