package com.uibk.databike

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.databike.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_list.*
import java.io.FileOutputStream
import java.io.PrintWriter

class ListActivity : AppCompatActivity() {
    private lateinit var dataPointViewModel: DataPointViewModel
    private val newDataPointActivityRequestCode = 1
    private val pickExportFile = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = DataPointListAdapter(this)
        recyclerview.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        dataPointViewModel =
            ViewModelProvider(this, DataPointViewModelFactory(this.application)).get(
                DataPointViewModel::class.java
            )
        dataPointViewModel.allDataPoints.observe(
            this,
            Observer { dataPoints -> dataPoints?.let { adapter.setDataPoints(it) } })

        findViewById<FloatingActionButton>(R.id.fab).let {
            it.setOnClickListener {
                val intent = Intent(this@ListActivity, NewDataPointActivity::class.java)
                startActivityForResult(intent, newDataPointActivityRequestCode)
            }
        }

        setSupportActionBar(findViewById(R.id.toolbar))
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
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
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
                val absX = data.getFloatExtra(NewDataPointActivity.ABS_X_REPLY, 0f)
                val absY = data.getFloatExtra(NewDataPointActivity.ABS_Y_REPLY, 0f)
                val absZ = data.getFloatExtra(NewDataPointActivity.ABS_Z_REPLY, 0f)

                dataPointViewModel.insert(DataPoint(0, absX, absY, absZ))
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
            val content = XmlBuilder.buildTrackFromPoints(dataPoints)
            val export = XmlBuilder.buildFullTrack(
                "visualizing_the_databike",
                "Generation Test Track",
                "Zesty",
                0,
                content
            )

            contentResolver.openFileDescriptor(uri, "w")?.use{ parcelFileDescriptor ->
                FileOutputStream(parcelFileDescriptor.fileDescriptor).use{ fileOutputStream ->
                    PrintWriter(fileOutputStream).use {printWriter ->
                        printWriter.print(export)
                    }
                }
            }
        }
    }
}