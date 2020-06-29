package com.uibk.databike.util

import android.content.Intent
import com.uibk.databike.data.DataPoint
import com.uibk.databike.view.NewDataPointActivity

const val SEGMENT = "com.uibk.databike.SEGMENT"
const val LATITUDE = "com.uibk.databike.LATITUDE"
const val LONGITUDE = "com.uibk.databike.LONGITUDE"
const val ELEVATION = "com.uibk.databike.ELEVATION"
const val TIME = "com.uibk.databike.TIME"
const val ABS_X = "com.uibk.databike.ABS_X"
const val ABS_Y = "com.uibk.databike.ABS_Y"
const val ABS_Z = "com.uibk.databike.ABS_Z"
const val ADD_X = "com.uibk.databike.ADD_X"
const val ADD_Y = "com.uibk.databike.ADD_Y"
const val ADD_Z = "com.uibk.databike.ADD_Z"
const val WHEEL_RPM = "com.uibk.databike.WHEEL_RPM"
const val STEERING_ROT = "com.uibk.databike.STEERING_ROT"
const val PEDAL_ROT = "com.uibk.databike.PEDAL_ROT"
const val PEDAL_ROT_DIR = "com.uibk.databike.PEDAL_ROT_DIR"
const val GEAR_FRONT = "com.uibk.databike.GEAR_FRONT"
const val GEAR_REAR = "com.uibk.databike.GEAR_REAR"
const val BRAKE_FRONT = "com.uibk.databike.BRAKE_FRONT"
const val BRAKE_REAR = "com.uibk.databike.BRAKE_REAR"
const val SUSPENSION_FRONT = "com.uibk.databike.SUSPENSION_FRONT"
const val SUSPENSION_REAR = "com.uibk.databike.SUSPENSION_REAR"
const val SEAT_POSITION = "com.uibk.databike.SEAT_POSITION"

fun addToIntent(intent: Intent, dataPoint: DataPoint) {
    intent.putExtra(SEGMENT, dataPoint.segment)
    intent.putExtra(LATITUDE, dataPoint.latitude)
    intent.putExtra(LONGITUDE, dataPoint.longitude)
    intent.putExtra(ELEVATION, dataPoint.elevation)
    intent.putExtra(TIME, dataPoint.timeStamp)
    intent.putExtra(ABS_X, dataPoint.absRotX)
    intent.putExtra(ABS_Y, dataPoint.absRotY)
    intent.putExtra(ABS_Z, dataPoint.absRotZ)
    intent.putExtra(ADD_X, dataPoint.addRotX)
    intent.putExtra(ADD_Y, dataPoint.addRotY)
    intent.putExtra(ADD_Z, dataPoint.addRotZ)
    intent.putExtra(WHEEL_RPM, dataPoint.wheelRpm)
    intent.putExtra(STEERING_ROT, dataPoint.steeringRot)
    intent.putExtra(PEDAL_ROT, dataPoint.pedalRot)
    intent.putExtra(PEDAL_ROT_DIR, dataPoint.pedalRotDir)
    intent.putExtra(GEAR_FRONT, dataPoint.gearFront)
    intent.putExtra(GEAR_REAR, dataPoint.gearRear)
    intent.putExtra(BRAKE_FRONT, dataPoint.brakeFront)
    intent.putExtra(BRAKE_REAR, dataPoint.brakeRear)
    intent.putExtra(SUSPENSION_FRONT, dataPoint.suspensionFront)
    intent.putExtra(SUSPENSION_REAR, dataPoint.suspensionRear)
    intent.putExtra(SEAT_POSITION, dataPoint.seatPosition)
}

fun getDataPointFromIntent(data: Intent): DataPoint {
    return DataPoint(
        0,
        data.getIntExtra(SEGMENT, 0),
        data.getFloatExtra(LATITUDE, 0f),
        data.getFloatExtra(LONGITUDE, 0f),
        data.getIntExtra(ELEVATION, 0),
        data.getStringExtra(TIME) ?: "",
        data.getFloatExtra(ABS_X, 0f),
        data.getFloatExtra(ABS_Y, 0f),
        data.getFloatExtra(ABS_Z, 0f),
        data.getFloatExtra(ADD_X, 0f),
        data.getFloatExtra(ADD_Y, 0f),
        data.getFloatExtra(ADD_Z, 0f),
        data.getFloatExtra(WHEEL_RPM, 0f),
        data.getFloatExtra(STEERING_ROT, 0f),
        data.getFloatExtra(PEDAL_ROT, 0f),
        data.getIntExtra(PEDAL_ROT_DIR, 0),
        data.getIntExtra(GEAR_FRONT, 0),
        data.getIntExtra(GEAR_REAR, 0),
        data.getFloatExtra(BRAKE_FRONT, 0f),
        data.getFloatExtra(BRAKE_REAR, 0f),
        data.getFloatExtra(SUSPENSION_FRONT, 0f),
        data.getFloatExtra(SUSPENSION_REAR, 0f),
        data.getFloatExtra(SEAT_POSITION, 0f)
    )
}
