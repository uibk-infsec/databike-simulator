package com.example.compass

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class NewDataPointActivity : Activity() {

    private lateinit var absRotXValue: EditText
    private lateinit var absRotYValue: EditText
    private lateinit var absRotZValue: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_datapoint)


        absRotXValue = findViewById(R.id.absRotXValue)
        absRotYValue = findViewById(R.id.absRotYValue)
        absRotZValue = findViewById(R.id.absRotZValue)
        findViewById<Button>(R.id.captureButton).let { it.setOnClickListener { addEntry() } }
    }

    private fun addEntry() {
        val replyIntent = Intent()

        val absRotXString = absRotXValue.text.toString()
        val absRotYString = absRotYValue.text.toString()
        val absRotZString = absRotZValue.text.toString()

        val absRotX: Float = if (absRotXString.isNotEmpty()) absRotXString.toFloat() else 0f
        val absRotY: Float = if (absRotYString.isNotEmpty()) absRotYString.toFloat() else 0f
        val absRotZ: Float = if (absRotZString.isNotEmpty()) absRotZString.toFloat() else 0f

        replyIntent.putExtra(ABS_X_REPLY, absRotX)
        replyIntent.putExtra(ABS_Y_REPLY, absRotY)
        replyIntent.putExtra(ABS_Z_REPLY, absRotZ)

        setResult(RESULT_OK, replyIntent)

        finish()
    }

    companion object {
        const val ABS_X_REPLY = "com.example.compass.ABS_X_REPLY"
        const val ABS_Y_REPLY = "com.example.compass.ABS_Y_REPLY"
        const val ABS_Z_REPLY = "com.example.compass.ABS_Z_REPLY"
    }
}