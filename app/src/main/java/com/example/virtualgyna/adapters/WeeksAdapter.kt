package com.example.virtualgyna.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualgyna.databinding.WeekItemBinding
import com.example.virtualgyna.models.WeekData

class WeeksAdapter(private val list: MutableList<WeekData>) :
    RecyclerView.Adapter<WeeksAdapter.WeekViewHolder>() {
    inner class WeekViewHolder(val weekItemBinding: WeekItemBinding) :
        RecyclerView.ViewHolder(weekItemBinding.root) {
        fun setData(week: WeekData) {
            weekItemBinding.apply {
                weeks.text = week.weeks
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        return WeekViewHolder(
            WeekItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        val week = list[position]
        holder.setData(week)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}