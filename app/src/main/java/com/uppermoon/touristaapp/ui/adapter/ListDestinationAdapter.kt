package com.uppermoon.touristaapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.uppermoon.touristaapp.data.dummy.Destination
import com.uppermoon.touristaapp.databinding.CardDestinationItemBinding
import com.uppermoon.touristaapp.databinding.ListDestinationItemBinding

class ListDestinationAdapter(private var listDestination: ArrayList<Destination>) :
    RecyclerView.Adapter<ListDestinationAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ListDestinationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ListViewHolder, position: Int) {
        val dataDestination = listDestination[position]
        viewHolder.apply {
            destinationItemBinding.apply {
                tvDestinationName.text = dataDestination.name
                tvDestinationLocation.text = dataDestination.city
                ivDestination.load(dataDestination.photo)
                tvDestinationDescription.text = dataDestination.description
            }
        }
    }

    override fun getItemCount(): Int = listDestination.size

    class ListViewHolder(val destinationItemBinding: ListDestinationItemBinding) :
        RecyclerView.ViewHolder(destinationItemBinding.root)
}