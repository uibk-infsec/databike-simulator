package com.uibk.databike.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.databike.R
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

        replyIntent.putExtra(SEGMENT_REPLY, extractFloatFromEditText(R.id.segment))
        replyIntent.putExtra(LATITUDE_REPLY, extractFloatFromEditText(R.id.latitude))
        replyIntent.putExtra(LONGITUDE_REPLY, extractFloatFromEditText(R.id.longitude))
        replyIntent.putExtra(ELEVATION_REPLY, extractFloatFromEditText(R.id.elevation))
        replyIntent.putExtra(TIME_REPLY, findViewById<EditText>(R.id.timeStamp).text.toString())
        replyIntent.putExtra(ABS_X_REPLY, extractFloatFromEditText(R.id.absRotX))
        replyIntent.putExtra(ABS_Y_REPLY, extractFloatFromEditText(R.id.absRotY))
        replyIntent.putExtra(ABS_Z_REPLY, extractFloatFromEditText(R.id.absRotZ))
        replyIntent.putExtra(ADD_X_REPLY, extractFloatFromEditText(R.id.addRotX))
        replyIntent.putExtra(ADD_Y_REPLY, extractFloatFromEditText(R.id.addRotY))
        replyIntent.putExtra(ADD_Z_REPLY, extractFloatFromEditText(R.id.addRotZ))
        replyIntent.putExtra(WHEEL_RPM_REPLY, extractFloatFromEditText(R.id.wheelRpm))
        replyIntent.putExtra(STEERING_ROT_REPLY, extractFloatFromEditText(R.id.steeringRot))
        replyIntent.putExtra(PEDAL_ROT_REPLY, extractFloatFromEditText(R.id.pedalRot))
        replyIntent.putExtra(PEDAL_ROT_DIR_REPLY, extractFloatFromEditText(R.id.pedalRotDir))
        replyIntent.putExtra(GEAR_FRONT_REPLY, extractFloatFromEditText(R.id.gearFront))
        replyIntent.putExtra(GEAR_REAR_REPLY, extractFloatFromEditText(R.id.gearRear))
        replyIntent.putExtra(BRAKE_FRONT_REPLY, extractFloatFromEditText(R.id.brakeFront))
        replyIntent.putExtra(BRAKE_REAR_REPLY, extractFloatFromEditText(R.id.brakeRear))
        replyIntent.putExtra(SUSPENSION_FRONT_REPLY, extractFloatFromEditText(R.id.suspensionFront))
        replyIntent.putExtra(SUSPENSION_REAR_REPLY, extractFloatFromEditText(R.id.suspensionRear))
        replyIntent.putExtra(SEAT_POSITION_REPLY, extractFloatFromEditText(R.id.seatPosition))


        setResult(RESULT_OK, replyIntent)

        finish()
    }

    companion object {
        const val SEGMENT_REPLY = "com.uibk.databike.SEGMENT_REPLY"
        const val LATITUDE_REPLY = "com.uibk.databike.LATITUDE_REPLY"
        const val LONGITUDE_REPLY = "com.uibk.databike.LONGITUDE_REPLY"
        const val ELEVATION_REPLY = "com.uibk.databike.ELEVATION_REPLY"
        const val TIME_REPLY = "com.uibk.databike.TIME_REPLY"
        const val ABS_X_REPLY = "com.uibk.databike.ABS_X_REPLY"
        const val ABS_Y_REPLY = "com.uibk.databike.ABS_Y_REPLY"
        const val ABS_Z_REPLY = "com.uibk.databike.ABS_Z_REPLY"
        const val ADD_X_REPLY = "com.uibk.databike.ADD_X_REPLY"
        const val ADD_Y_REPLY = "com.uibk.databike.ADD_Y_REPLY"
        const val ADD_Z_REPLY = "com.uibk.databike.ADD_Z_REPLY"
        const val WHEEL_RPM_REPLY = "com.uibk.databike.WHEEL_RPM_REPLY"
        const val STEERING_ROT_REPLY = "com.uibk.databike.STEERING_ROT_REPLY"
        const val PEDAL_ROT_REPLY = "com.uibk.databike.PEDAL_ROT_REPLY"
        const val PEDAL_ROT_DIR_REPLY = "com.uibk.databike.PEDAL_ROT_DIR_REPLY"
        const val GEAR_FRONT_REPLY = "com.uibk.databike.GEAR_FRONT_REPLY"
        const val GEAR_REAR_REPLY = "com.uibk.databike.GEAR_REAR_REPLY"
        const val BRAKE_FRONT_REPLY = "com.uibk.databike.BRAKE_FRONT_REPLY"
        const val BRAKE_REAR_REPLY = "com.uibk.databike.BRAKE_REAR_REPLY"
        const val SUSPENSION_FRONT_REPLY = "com.uibk.databike.SUSPENSION_FRONT_REPLY"
        const val SUSPENSION_REAR_REPLY = "com.uibk.databike.SUSPENSION_REAR_REPLY"
        const val SEAT_POSITION_REPLY = "com.uibk.databike.SEAT_POSITION_REPLY"

    }
}