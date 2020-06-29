package com.uibk.databike.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.databike.R
import com.uibk.databike.data.DataPoint

class DataPointListAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<DataPointListAdapter.DataPointViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var dataPoints = emptyList<DataPoint>()

    inner class DataPointViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val latitudeView: TextView = itemView.findViewById(R.id.latitude)
        val longitudeView: TextView = itemView.findViewById(R.id.longitude)
        val timeStampView: TextView = itemView.findViewById(R.id.timeStamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataPointViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return DataPointViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DataPointViewHolder, position: Int) {
        val current = dataPoints[position]
        holder.latitudeView.text = current.latitude.toString()
        holder.longitudeView.text = current.longitude.toString()
        holder.timeStampView.text = current.timeStamp
    }

    internal fun setDataPoints(dataPoints: List<DataPoint>) {
        this.dataPoints = dataPoints
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataPoints.size
}