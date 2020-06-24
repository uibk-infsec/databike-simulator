package com.example.compass

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {
    private lateinit var dataPointViewModel: DataPointViewModel

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
            Toast.makeText(this, "Exporting data", Toast.LENGTH_SHORT).show()

            // TODO: export data
            val dataPoints = dataPointViewModel.allDataPoints.value!!
            Log.v("Export", dataPoints[0].getXml())
        }
        return true
    }

    private val newDataPointActivityRequestCode = 1

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
    }
}