package com.uibk.databike.sensors

import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.IBinder
import android.os.Parcel
import java.time.Instant

class LocationService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return LocationBinder()
    }

    fun getLocation(): Location {
        val ret = Location("")
        ret.latitude = 47.259659
        ret.longitude = 11.400375
        ret.altitude = 545.0
        ret.time = Instant.now().epochSecond

        return ret
    }

    inner class LocationBinder: Binder(){
        fun getService(): LocationService{
            return this@LocationService
        }
    }
}