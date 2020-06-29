package com.uibk.databike.view

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.example.databike.R
import com.uibk.databike.util.getDataPointFromIntent

class ViewDataPointActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_view_datapoint)

        val dataPoint = getDataPointFromIntent(intent)
        findViewById<TextView>(R.id.segment).text = dataPoint.segment.toString()
        findViewById<TextView>(R.id.latitude).text = dataPoint.latitude.toString()
        findViewById<TextView>(R.id.longitude).text = dataPoint.longitude.toString()
        findViewById<TextView>(R.id.timeStamp).text = dataPoint.timeStamp
        findViewById<TextView>(R.id.absRotX).text = dataPoint.absRotX.toString()
        findViewById<TextView>(R.id.absRotY).text = dataPoint.absRotY.toString()
        findViewById<TextView>(R.id.absRotZ).text = dataPoint.absRotZ.toString()
        findViewById<TextView>(R.id.addRotX).text = dataPoint.addRotX.toString()
        findViewById<TextView>(R.id.addRotY).text = dataPoint.addRotY.toString()
        findViewById<TextView>(R.id.addRotZ).text = dataPoint.addRotZ.toString()
        findViewById<TextView>(R.id.wheelRpm).text = dataPoint.wheelRpm.toString()
        findViewById<TextView>(R.id.steeringRot).text = dataPoint.steeringRot.toString()
        findViewById<TextView>(R.id.pedalRot).text = dataPoint.pedalRot.toString()
        findViewById<TextView>(R.id.pedalRotDir).text = dataPoint.pedalRot.toString()
        findViewById<TextView>(R.id.gearFront).text = dataPoint.gearFront.toString()
        findViewById<TextView>(R.id.gearRear).text = dataPoint.gearRear.toString()
        findViewById<TextView>(R.id.brakeFront).text = dataPoint.brakeFront.toString()
        findViewById<TextView>(R.id.brakeRear).text = dataPoint.brakeRear.toString()
        findViewById<TextView>(R.id.suspensionFront).text = dataPoint.suspensionFront.toString()
        findViewById<TextView>(R.id.suspensionRear).text = dataPoint.suspensionRear.toString()
        findViewById<TextView>(R.id.seatPosition).text = dataPoint.seatPosition.toString()
    }


}