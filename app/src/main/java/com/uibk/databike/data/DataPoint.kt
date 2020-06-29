package com.uibk.databike.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.format.DateTimeFormatter


@Entity(tableName = "data_point")
data class DataPoint(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val segmentId: Int,
    val latitude: Float,
    val longitude: Float,
    val elevation: Int,
    val timeStamp: String,
    val absRotX: Float,
    val absRotY: Float,
    val absRotZ: Float,
    val addRotX: Float = 0f,
    val addRotY: Float = 0f,
    val addRotZ: Float = 0f,
    val wheelRpm: Float = 0f,
    val steeringRot: Float = 0f,
    val pedalRot: Float = 0f,
    val pedalRotDir: Int = 1,
    val gearFront: Int = 1,
    val gearRear: Int = 1,
    val brakeFront: Float = 0f,
    val brakeRear: Float = 0f,
    val suspensionFront: Float = 0f,
    val suspensionRear: Float = 0f,
    val seatPosition: Float = 0f
)