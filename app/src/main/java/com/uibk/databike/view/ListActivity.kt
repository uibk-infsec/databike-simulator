package com.uibk.databike.view

import android.Manifest
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.databike.R
import com.google.android.gms.location.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.uibk.databike.data.DataPointViewModel
import com.uibk.databike.data.DataPointViewModelFactory
import com.uibk.databike.sensors.LocationService
import com.uibk.databike.util.*
import kotlinx.android.synthetic.main.activity_list.*
import java.io.FileOutputStream
import java.io.PrintWriter
import java.time.Instant

class ListActivity : AppCompatActivity() {
    private lateinit var dataPointViewModel: DataPointViewModel

    private lateinit var locationService: LocationService
    private var isLocationServiceBound: Boolean = false
    private val locationServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            isLocationServiceBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as LocationService.LocationBinder
            locationService = binder.getService()
            isLocationServiceBound = true
        }


    }

    private val newDataPointActivityRequestCode = 1
    private val requestPermissionRequestCode = 2
    private val pickExportFile = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = DataPointListAdapter(this)
        recyclerview.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        dataPointViewModel =
            ViewModelProvider(
                this,
                DataPointViewModelFactory(this.application)
            ).get(
                DataPointViewModel::class.java
            )
        dataPointViewModel.allDataPoints.observe(
            this,
            Observer { dataPoints -> dataPoints?.let { adapter.setDataPoints(it) } })

        findViewById<FloatingActionButton>(R.id.fab).let {
            it.setOnClickListener {
                val intent =
                    Intent(this@ListActivity, NewDataPointActivity::class.java).also { intent ->
                        if (isLocationServiceBound)
                            locationService.getLocation().let { location ->
                                intent.putExtra(LATITUDE, location.latitude.toString())
                                intent.putExtra(LONGITUDE, location.longitude.toString())
                                intent.putExtra(ELEVATION, location.altitude.toString())
                                intent.putExtra(
                                    TIME,
                                    Instant.ofEpochMilli(location.time).toString()
                                )
                            }
                    }
                startActivityForResult(intent, newDataPointActivityRequestCode)
            }
        }

        setSupportActionBar(findViewById(R.id.toolbar))

        requestLocationPermissionsIfNecessary()
    }

    override fun onStart() {
        super.onStart()
        Intent(this, LocationService::class.java).also { intent ->
            bindService(intent, locationServiceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(locationServiceConnection)
        isLocationServiceBound = false
    }

    private fun requestLocationPermissionsIfNecessary() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                requestPermissionRequestCode
            )

            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(
                    this,
                    "We want the most accurate position possible",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        if (item.title == getString(R.string.action_export)) {
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/gpx"
                flags =
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                putExtra(Intent.EXTRA_TITLE, "trace.gpx")
            }
            startActivityForResult(intent, pickExportFile)
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newDataPointActivityRequestCode && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                dataPointViewModel.insert(
                    getDataPointFromIntent(data)
                )
            } else {
                Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_SHORT)
                    .show()
            }

        }

        if (requestCode == pickExportFile) {
            Log.v("Export", "Got response from file picker")
            val uri = data?.data
            if (uri == null) {
                Log.e("Export", "Did not receive a URI")
                return
            }

            val dataPoints = dataPointViewModel.allDataPoints.value!!
            val content =
                XmlBuilder.buildTrackFromPoints(dataPoints)
            val export = XmlBuilder.buildFullTrack(
                "visualizing_the_databike",
                "Generation Test Track",
                "Zesty",
                0,
                content
            )

            contentResolver.openFileDescriptor(uri, "w")?.use { parcelFileDescriptor ->
                FileOutputStream(parcelFileDescriptor.fileDescriptor).use { fileOutputStream ->
                    PrintWriter(fileOutputStream).use { printWriter ->
                        printWriter.print(export)
                    }
                }
            }
        }
    }
}