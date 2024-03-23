package com.example.virtualgyna.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualgyna.databinding.UpdatesItemBinding
import com.example.virtualgyna.models.UpdatesData

class UpdatesAdapter(private val list: MutableList<UpdatesData>) :
    RecyclerView.Adapter<UpdatesAdapter.UpdateViewHolder>() {

    inner class UpdateViewHolder(val updatesItemBinding: UpdatesItemBinding) :
        RecyclerView.ViewHolder(updatesItemBinding.root) {
        fun setData(update: UpdatesData) {
            updatesItemBinding.apply {
                metrics.text = update.metrics
                visits.text = update.visits
                milestones.text = update.milestones
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpdateViewHolder {
        return UpdateViewHolder(
            UpdatesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UpdateViewHolder, position: Int) {
        val update = list[position]
        holder.setData(update)
    }

    override fun getItemCount(): Int {
        return list.size
    }


}