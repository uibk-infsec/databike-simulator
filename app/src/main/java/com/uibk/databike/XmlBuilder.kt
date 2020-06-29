package com.uibk.databike

import com.uibk.databike.data.DataPoint
import kotlin.text.StringBuilder

object XmlBuilder {
    //TODO: clean this up
    @JvmStatic
    fun buildFullTrack(creator: String, name: String, bikeName: String, pedalOffset: Int, content: String): String {
        val builder = StringBuilder()

        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
        builder.append("<gpx version=\"1.1\" creator=\"$creator\">\n")
        builder.append("<metadata>\n")
        builder.append("\t<name>$name</name>\n")
        builder.append("\t<bikename>$bikeName</bikename>\n")
        builder.append("\t<pedalOffset>$pedalOffset</pedalOffset>\n")
        builder.append("</metadata>\n")
        builder.append("$content")
        builder.append("</gpx>")
        return builder.toString()
    }

    @JvmStatic
    fun buildTrackFromSegments(segments: List<List<DataPoint>>): String {
        val builder = StringBuilder()

        builder.append("<trk>\n")
        for (segment in segments)
            builder.append(buildTrackSegment(segment))
        builder.append("</trk>\n")

        return builder.toString()
    }

    @JvmStatic
    fun buildTrackFromPoints(dataPoints: List<DataPoint>): String {
        val builder = StringBuilder()

        builder.append("<trk>\n")
        builder.append(buildTrackSegment(dataPoints))
        builder.append("</trk>\n")

        return builder.toString()
    }

    @JvmStatic
    fun buildTrackSegment(dataPoints: List<DataPoint>): String {
        val builder = StringBuilder()

        builder.append("<trkseg>\n")

        for (dataPoint in dataPoints) {
            builder.append(fromDataPoint(dataPoint))
        }

        builder.append("</trkseg>\n")
        return builder.toString()
    }

    fun fromDataPoint(dataPoint: DataPoint): String {
        val builder = StringBuilder()

        builder.append("<trkpt lat=\"${dataPoint.latitude}\" lon=\"${dataPoint.longitude}\">\n")
        builder.append("\t<ele>${dataPoint.elevation}</ele>\n")
        builder.append("\t<time>${dataPoint.timeStamp}</time>\n")
        builder.append("\t<extensions>\n")

        builder.append("\t\t<absrotx>${dataPoint.absRotX}</absrotx>\n")
        builder.append("\t\t<absroty>${dataPoint.absRotY}</absroty>\n")
        builder.append("\t\t<absroty>${dataPoint.absRotY}</absroty>\n")
        builder.append("\t\t<addrotx>${dataPoint.addRotX}</addrotx>\n")
        builder.append("\t\t<addroty>${dataPoint.addRotY}</addroty>\n")
        builder.append("\t\t<addrotz>${dataPoint.addRotZ}</addrotz>\n")
        builder.append("\t\t<wheelrpm>${dataPoint.wheelRpm}</wheelrpm>\n")
        builder.append("\t\t<steeringrot>${dataPoint.steeringRot}</steeringrot>\n")
        builder.append("\t\t<pedalrot>${dataPoint.pedalRot}</pedalrot>\n")
        builder.append("\t\t<pedalrotdir>${dataPoint.pedalRotDir}</pedalrotdir>\n")
        builder.append("\t\t<gearfront>${dataPoint.gearFront}</gearfront>\n")
        builder.append("\t\t<gearrear>${dataPoint.gearRear}</gearrear>\n")
        builder.append("\t\t<brakerear>${dataPoint.brakeRear}</brakerear>\n")
        builder.append("\t\t<brakefront>${dataPoint.brakeFront}</brakefront>\n")
        builder.append("\t\t<suspfront>${dataPoint.suspensionFront}</suspfront>\n")
        builder.append("\t\t<susprear>${dataPoint.suspensionRear}</susprear>\n")
        builder.append("\t\t<seatpos>${dataPoint.seatPosition}</seatposition>\n")

        builder.append("\t</extensions>\n")
        builder.append("</trkpt>\n")

        return builder.toString()
    }
}