package com.example.compass

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
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
            it.setOnClickListener(View.OnClickListener {
                val intent = Intent(this@ListActivity, NewDataPointActivity::class.java)
                startActivityForResult(intent, newDataPointActivityRequestCode)
            })
        }
    }

    private val newDataPointActivityRequestCode = 1

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newDataPointActivityRequestCode && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val absX = data.getFloatExtra(NewDataPointActivity.ABS_X_REPLY, 0f)
                val absY = data.getFloatExtra(NewDataPointActivity.ABS_Y_REPLY, 0f)
                val absZ = data.getFloatExtra(NewDataPointActivity.ABS_Z_REPLY, 0f)

                if (absX != null && absY != null && absZ != null)
                    dataPointViewModel.insert(DataPoint(0, absX, absY, absZ))
                else
                    Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_SHORT)
                        .show()
            } else {
                Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }
}