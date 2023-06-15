package com.uppermoon.touristaapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uppermoon.touristaapp.data.dummy.Recresult
import com.uppermoon.touristaapp.databinding.ListRecommendationItemBinding

class ListRecresultAdapter(private val listRecresult: List<Recresult>) :
    RecyclerView.Adapter<ListRecresultAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListRecommendationItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ListRecresultAdapter.ViewHolder(binding)

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val dataRecresult = listRecresult[position]
        viewHolder.apply {
            recresultBinding.apply {
                tvDestinationName.text = dataRecresult.recresultName
                tvType.text = dataRecresult.recresultType
                Glide.with(itemView.context)
                    .load(dataRecresult.recresultPhoto) // URL Gambar
                    .into(ivDestination)
            }
        }
    }

    override fun getItemCount(): Int = listRecresult.size

    class ViewHolder(val recresultBinding: ListRecommendationItemBinding) :
        RecyclerView.ViewHolder(recresultBinding.root)

}