package com.uppermoon.touristaapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.uppermoon.touristaapp.data.dummy.Destination
import com.uppermoon.touristaapp.databinding.ListDestinationItemBinding
import com.uppermoon.touristaapp.presentation.detail.DetailActivity

class ListDummyNearDestinationAdapter (private var listDestination: List<Destination>) :
    RecyclerView.Adapter<ListDummyNearDestinationAdapter.ListViewHolder>(){

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

            itemView.setOnClickListener {
                val data = Destination(
                    dataDestination.name,
                    dataDestination.city,
                    dataDestination.photo,
                    dataDestination.description
                )
                val intentDetail = Intent(itemView.context, DetailActivity::class.java)
                intentDetail.putExtra(DetailActivity.EXTRA_DETAIL, data)
                itemView.context.startActivity(intentDetail)
            }
        }
    }

    override fun getItemCount(): Int = listDestination.size

    class ListViewHolder(val destinationItemBinding: ListDestinationItemBinding) :
        RecyclerView.ViewHolder(destinationItemBinding.root)
}