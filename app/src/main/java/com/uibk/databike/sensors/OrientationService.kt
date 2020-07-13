package com.uibk.databike.sensors

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Binder
import android.os.IBinder
import com.uibk.databike.exceptions.MissingPermissionException

class OrientationService : Service(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var magneticSensor: Sensor
    private val magneticReading = FloatArray(3)
    private lateinit var accelerationSensor: Sensor
    private val accelerationReading = FloatArray(3)

    private val rotationMatrix = FloatArray(9)
    private val orientation = FloatArray(3)

    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD).also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        accelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER).also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return OrientationBinder()
    }

    fun getOrientation(): FloatArray {
        return orientation
    }

    inner class OrientationBinder : Binder() {
        fun getService(): OrientationService {
            return this@OrientationService
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // ignore
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER)
            System.arraycopy(
                event.values,
                0,
                accelerationReading,
                0,
                accelerationReading.size
            )
        else if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD)
            System.arraycopy(
                event.values,
                0,
                magneticReading,
                0,
                magneticReading.size
            )

        updateOrientation()
    }

    private fun updateOrientation() {
        SensorManager.getRotationMatrix(
            rotationMatrix,
            null,
            accelerationReading,
            magneticReading
        )
        val tmpOrientation = FloatArray(3)
        SensorManager.getOrientation(rotationMatrix, tmpOrientation)
        for (i in 1 until tmpOrientation.size)
            orientation[i] = tmpOrientation[i] * 180 / Math.PI.toFloat()
    }
}