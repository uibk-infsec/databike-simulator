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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(), SensorEventListener {
    // sensors
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    // sensor values
    private var gravity: FloatArray = FloatArray(3)

    // text Views
    private lateinit var xTextView: TextView
    private lateinit var yTextView: TextView
    private lateinit var zTextView: TextView

    // rendering
    private lateinit var arrowView: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (accelerometer != null) {
            Toast.makeText(this, "Sensors successfully created", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Could not create sensors", Toast.LENGTH_SHORT).show()
        }

//        arrowView = ArrowView(this, rotationMatrix)
//        setContentView(arrowView)
        setContentView(R.layout.activity_main)
        xTextView = findViewById(R.id.xValue)
        yTextView = findViewById(R.id.yValue)
        zTextView = findViewById(R.id.zValue)
    }

    override fun onResume() {
        super.onResume()

        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
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
        gravity = event.values

        val x = gravity[0]
        val y = gravity[1]
        val z = gravity[2]

        xTextView.text = getString(R.string.degree_template).format(x)
        yTextView.text = getString(R.string.degree_template).format(y)
        zTextView.text = getString(R.string.degree_template).format(z)
    }
}