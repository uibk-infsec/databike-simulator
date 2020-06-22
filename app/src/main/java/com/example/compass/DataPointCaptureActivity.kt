package com.example.compass

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

class DataPointCaptureActivity : Activity() {

    private lateinit var absRotXValue: EditText
    private lateinit var absRotYValue: EditText
    private lateinit var absRotZValue: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_capture)


        absRotXValue = findViewById(R.id.absRotXValue)
        absRotYValue = findViewById(R.id.absRotYValue)
        absRotZValue = findViewById(R.id.absRotZValue)
        findViewById<Button>(R.id.captureButton).let { it.setOnClickListener { addEntry() } }
    }

    private fun addEntry() {
        val absRotX: Float = absRotXValue.text.toString().toFloat()
        val absRotY: Float = absRotYValue.text.toString().toFloat()
        val absRotZ: Float = absRotZValue.text.toString().toFloat()

        val newDataPoint = DataPoint(0, absRotX, absRotY, absRotZ)
    }
}