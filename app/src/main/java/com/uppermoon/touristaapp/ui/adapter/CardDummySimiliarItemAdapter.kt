package com.uppermoon.touristaapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uppermoon.touristaapp.data.dummy.Similiar
import com.uppermoon.touristaapp.databinding.CardDestinationItemBinding

class CardDummySimiliarItemAdapter (private val listSimiliar: List<Similiar>) :
    RecyclerView.Adapter<CardDummySimiliarItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CardDestinationItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val dataSimiliar = listSimiliar[position]
        viewHolder.apply {
            similiarBinding.apply {
                tvDestinationName.text = dataSimiliar.similiarName
                tvDestinationLocation.text = dataSimiliar.similiarCity
                Glide.with(itemView.context)
                    .load(dataSimiliar.similiarPhoto) // URL Gambar
                    .into(ivDestination)

            }
        }
    }

    override fun getItemCount(): Int = listSimiliar.size

    class ViewHolder(val similiarBinding: CardDestinationItemBinding) :
        RecyclerView.ViewHolder(similiarBinding.root)
}