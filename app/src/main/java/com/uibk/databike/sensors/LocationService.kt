package com.uibk.databike.sensors

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Binder
import android.os.IBinder
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.uibk.databike.exceptions.MissingPermissionException
import java.time.Instant

class LocationService : Service() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var lastLocation: Location? = null
    private var locationRequest: LocationRequest? = null
    private lateinit var locationCallback: LocationCallback

    override fun onBind(intent: Intent?): IBinder? {
        return LocationBinder()
    }

    override fun onCreate() {
        super.onCreate()
        locationRequest = LocationRequest.create()?.apply {
            interval = 5000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.let { lastLocation = locationResult.lastLocation }
            }
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            throw MissingPermissionException("Necessary location permissions not granted")
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    fun getLocation(): Location {
        lastLocation?.let { return it }
        // ? should we return the default here or fail?
        val ret = Location("")
        ret.latitude = 47.259659
        ret.longitude = 11.400375
        ret.altitude = 545.0
        ret.time = Instant.now().epochSecond

        return ret
    }

    inner class LocationBinder : Binder() {
        fun getService(): LocationService {
            return this@LocationService
        }
    }
}