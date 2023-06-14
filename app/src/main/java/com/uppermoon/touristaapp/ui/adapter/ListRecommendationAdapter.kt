package com.uppermoon.touristaapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uppermoon.touristaapp.data.network.response.DestinationResponseItem
import com.uppermoon.touristaapp.data.network.response.TripRecommendationResponseItem
import com.uppermoon.touristaapp.databinding.ListRecommendationItemBinding
import com.uppermoon.touristaapp.presentation.detail.DetailActivity

class ListRecommendationAdapter(private var listRecommendation: List<TripRecommendationResponseItem>): RecyclerView.Adapter<ListRecommendationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListRecommendationItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataRecommendation = listRecommendation[position]
        holder.apply {
            recommendBinding.apply {
                tvDestinationName.text = dataRecommendation.nameWisata
                tvType.text = dataRecommendation.tripNameType
            }

            itemView.setOnClickListener {
                val destination = DestinationResponseItem(
                    dataRecommendation.nameWisata,
                    dataRecommendation.destination.destinationPhoto,
                    dataRecommendation.destination.coordinate,
                    dataRecommendation.destination.timeMinutes,
                    dataRecommendation.destination.city,
                    dataRecommendation.destination.price,
                    dataRecommendation.destination.descriptionWisata,
                    dataRecommendation.destination.rating,
                    dataRecommendation.destination.destinationLat,
                    dataRecommendation.destination.destinationLong,
                    dataRecommendation.destination.id,
                    dataRecommendation.destination.category
                )
                val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
                intentDetail.putExtra(DetailActivity.EXTRA_DETAIL, destination)
                holder.itemView.context.startActivity(intentDetail)
            }
        }
    }

    override fun getItemCount(): Int = listRecommendation.size

    class ViewHolder(val recommendBinding: ListRecommendationItemBinding) :
        RecyclerView.ViewHolder(recommendBinding.root)
}
