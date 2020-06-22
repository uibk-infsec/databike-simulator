package com.example.compass

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataPointListAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<DataPointListAdapter.DataPointViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var dataPoints = emptyList<DataPoint>()

    inner class DataPointViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dataPointItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataPointViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return DataPointViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DataPointViewHolder, position: Int) {
        val current = dataPoints[position]
        holder.dataPointItemView.text = current.absRotX.toString()
    }

    internal fun setDataPoints(dataPoints: List<DataPoint>) {
        this.dataPoints = dataPoints
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataPoints.size
}