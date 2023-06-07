package com.uppermoon.touristaapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.uppermoon.touristaapp.data.dummy.Destination
import com.uppermoon.touristaapp.databinding.CardDestinationItemBinding

class CardDestinationAdapter(private var cardDestination: ArrayList<Destination>) :
    RecyclerView.Adapter<CardDestinationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardDestinationItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val dataDestination = cardDestination[position]
        viewHolder.apply {
            destinationItemBinding.apply {
                tvDestinationName.text = dataDestination.name
                tvDestinationLocation.text = dataDestination.city
                ivDestination.load(dataDestination.photo)
            }
        }
    }

    override fun getItemCount(): Int = cardDestination.size

    class ViewHolder(val destinationItemBinding: CardDestinationItemBinding) : RecyclerView.ViewHolder(destinationItemBinding.root)
}