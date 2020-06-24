package com.example.compass

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.StringBuilder


@Entity(tableName = "data_point")
data class DataPoint(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val absRotX: Float,
    val absRotY: Float,
    val absRotZ: Float
) {
    fun getXml(): String {
        // dummy values for now
        val lat = 47.263308
        val lon = 11.353000
        val elevation = 500
        val time = "2020-04-17T14:20:00.000"
        val addRotX = 0
        val addRotY = 0
        val addRotZ = 0
        val wheelRpm = 0
        val steeringRot = 0
        val pedalRot = 0
        val pedalRotDir = 1
        val gearFront = 1
        val gearRear = 1
        val brakeFront = 0
        val brakeRear = 0
        val suspensionFront = 0
        val suspensionRear = 0
        val seatPosition = 0

        val builder = StringBuilder()

        builder.append("<trkpt lat=\"$lat\" lon=\"$lon\">\n")
        builder.append("\t<ele>$elevation</ele>\n")
        builder.append("\t<time>$time</time>\n")
        builder.append("\t<extensions>\n")

        builder.append("\t\t<absrotx>$absRotX</absrotx>\n")
        builder.append("\t\t<absroty>$absRotY</absroty>\n")
        builder.append("\t\t<absroty>$absRotY</absroty>\n")
        builder.append("\t\t<addrotx>$addRotX</addrotx>\n")
        builder.append("\t\t<addroty>$addRotY</addroty>\n")
        builder.append("\t\t<addrotz>$addRotZ</addrotz>\n")
        builder.append("\t\t<wheelrpm>$wheelRpm</wheelrpm>\n")
        builder.append("\t\t<steeringrot>$steeringRot</steeringrot>\n")
        builder.append("\t\t<pedalrot>$pedalRot</pedalrot>\n")
        builder.append("\t\t<pedalrotdir>$pedalRotDir</pedalrotdir>\n")
        builder.append("\t\t<gearfront>$gearFront</gearfront>\n")
        builder.append("\t\t<gearrear>$gearRear</gearrear>\n")
        builder.append("\t\t<brakerear>$brakeRear</brakerear>\n")
        builder.append("\t\t<brakefront>$brakeFront</brakefront>\n")
        builder.append("\t\t<suspfront>$suspensionFront</suspfront>\n")
        builder.append("\t\t<susprear>$suspensionRear</susprear>\n")
        builder.append("\t\t<seatpos>$seatPosition</seatposition>\n")

        builder.append("\t</extensions>\n")
        builder.append("</trkpt>\n")

        return builder.toString()
    }
}