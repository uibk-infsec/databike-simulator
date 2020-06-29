package com.uibk.databike.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.databike.R
import com.uibk.databike.util.*
import java.time.Instant
import java.time.format.DateTimeFormatter

class NewDataPointActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_datapoint)


        findViewById<EditText>(R.id.timeStamp).setText(
            DateTimeFormatter.ISO_INSTANT.format(
                Instant.now()
            ), TextView.BufferType.EDITABLE
        )

        findViewById<Button>(R.id.captureButton).setOnClickListener { addEntry() }
    }

    private fun extractFloatFromEditText(id: Int): Float {
        return findViewById<EditText>(id).text.toString().toFloatOrNull() ?: 0f
    }

    private fun addEntry() {
        val replyIntent = Intent()

        replyIntent.putExtra(SEGMENT, extractFloatFromEditText(R.id.segment))
        replyIntent.putExtra(LATITUDE, extractFloatFromEditText(R.id.latitude))
        replyIntent.putExtra(LONGITUDE, extractFloatFromEditText(R.id.longitude))
        replyIntent.putExtra(ELEVATION, extractFloatFromEditText(R.id.elevation))
        replyIntent.putExtra(TIME, findViewById<EditText>(R.id.timeStamp).text.toString())
        replyIntent.putExtra(ABS_X, extractFloatFromEditText(R.id.absRotX))
        replyIntent.putExtra(ABS_Y, extractFloatFromEditText(R.id.absRotY))
        replyIntent.putExtra(ABS_Z, extractFloatFromEditText(R.id.absRotZ))
        replyIntent.putExtra(ADD_X, extractFloatFromEditText(R.id.addRotX))
        replyIntent.putExtra(ADD_Y, extractFloatFromEditText(R.id.addRotY))
        replyIntent.putExtra(ADD_Z, extractFloatFromEditText(R.id.addRotZ))
        replyIntent.putExtra(WHEEL_RPM, extractFloatFromEditText(R.id.wheelRpm))
        replyIntent.putExtra(STEERING_ROT, extractFloatFromEditText(R.id.steeringRot))
        replyIntent.putExtra(PEDAL_ROT, extractFloatFromEditText(R.id.pedalRot))
        replyIntent.putExtra(PEDAL_ROT_DIR, extractFloatFromEditText(R.id.pedalRotDir))
        replyIntent.putExtra(GEAR_FRONT, extractFloatFromEditText(R.id.gearFront))
        replyIntent.putExtra(GEAR_REAR, extractFloatFromEditText(R.id.gearRear))
        replyIntent.putExtra(BRAKE_FRONT, extractFloatFromEditText(R.id.brakeFront))
        replyIntent.putExtra(BRAKE_REAR, extractFloatFromEditText(R.id.brakeRear))
        replyIntent.putExtra(SUSPENSION_FRONT, extractFloatFromEditText(R.id.suspensionFront))
        replyIntent.putExtra(SUSPENSION_REAR, extractFloatFromEditText(R.id.suspensionRear))
        replyIntent.putExtra(SEAT_POSITION, extractFloatFromEditText(R.id.seatPosition))


        setResult(RESULT_OK, replyIntent)

        finish()
    }
}