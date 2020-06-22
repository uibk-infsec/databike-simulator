package com.example.compass

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.opengl.GLSurfaceView
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

    private var rotationMatrix = FloatArray(16)

    // rendering
    private lateinit var arrowView: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR)
        if (accelerometer != null && magnetometer != null) {
            Toast.makeText(this, "Sensors successfully created", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Could not create sensors", Toast.LENGTH_SHORT).show()
        }

//        arrowView = ArrowView(this, rotationMatrix)
//        setContentView(arrowView)
        setContentView(R.layout.activity_main)
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
        val i = FloatArray(9)
        SensorManager.getRotationMatrix(rotationMatrix, i, gravity, geomagnetic)

        val orientation = FloatArray(3)
        SensorManager.getOrientation(rotationMatrix, orientation)

        val cleanedOrientation = orientation.map { x -> x * 180 / Math.PI }

        val azimuth = cleanedOrientation[0]
        val pitch = cleanedOrientation[1]
        val roll = cleanedOrientation[2]

        val azimuthValue = findViewById<TextView>(R.id.azimuthValue)
        val pitchValue = findViewById<TextView>(R.id.pitchValue)
        val rollValue = findViewById<TextView>(R.id.rollValue)

        azimuthValue.text = getString(R.string.degree_template).format(azimuth)
        pitchValue.text = getString(R.string.degree_template).format(pitch)
        rollValue.text = getString(R.string.degree_template).format(roll)
    }
}