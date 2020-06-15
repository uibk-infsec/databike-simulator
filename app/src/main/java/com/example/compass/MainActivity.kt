package com.example.compass

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity(), SensorEventListener {
    // sensors
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var magnetometer: Sensor? = null

    // sensor values
    private var gravity: FloatArray = FloatArray(3)
    private var geomagnetic: FloatArray = FloatArray(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR)
        if (accelerometer != null && magnetometer != null) {
            Toast.makeText(this, "Sensors successfully created", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Could not create sensors", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()

        if (accelerometer != null && magnetometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL)
        } else {
            TODO("Sensors not available, implement error handling")
        }
    }

    override fun onPause() {
        super.onPause()

        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
//        TODO("Accuracy Not yet implemented")
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor == accelerometer)
            gravity = event.values
        if (event.sensor == magnetometer)
            geomagnetic = event.values

        updateValues()
    }

    private fun updateValues() {
        val r = FloatArray(9)
        val i = FloatArray(9)
        SensorManager.getRotationMatrix(r, i, gravity, geomagnetic)
        val orientation = FloatArray(3)
        SensorManager.getOrientation(r, orientation)

        val azimuthValue = findViewById<TextView>(R.id.azimuthValue)
        val pitchValue = findViewById<TextView>(R.id.pitchValue)
        val rollValue = findViewById<TextView>(R.id.rollValue)

        azimuthValue.text = orientation[0].toString()
        pitchValue.text = orientation[1].toString()
        rollValue.text = orientation[2].toString()
    }
}