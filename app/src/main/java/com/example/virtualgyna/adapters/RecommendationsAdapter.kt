package com.example.virtualgyna.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualgyna.databinding.RecomendationsItemBinding
import com.example.virtualgyna.models.UpdatesData

class RecommendationsAdapter(private val list: MutableList<UpdatesData>) :
    RecyclerView.Adapter<RecommendationsAdapter.RecommendationsViewHolder>() {

    inner class RecommendationsViewHolder(val recomendationsItemBinding: RecomendationsItemBinding) :
        RecyclerView.ViewHolder(recomendationsItemBinding.root) {
        fun setData(recommend: UpdatesData) {
            recomendationsItemBinding.apply {
                weightRecommendation.text = recommend.weightRecommendation
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationsViewHolder {
        return RecommendationsViewHolder(
            RecomendationsItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecommendationsViewHolder, position: Int) {
        val recommend = list[position]
        holder.setData(recommend)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}