package com.uppermoon.touristaapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.uppermoon.touristaapp.R
import com.uppermoon.touristaapp.data.network.response.DestinationResponse
import com.uppermoon.touristaapp.data.network.response.DestinationResponseItem
import com.uppermoon.touristaapp.databinding.CardDestinationItemBinding
import com.uppermoon.touristaapp.presentation.detail.DetailActivity

class CardDestinationAdapter : RecyclerView.Adapter<CardDestinationAdapter.ViewHolder>() {

    private var listData = ArrayList<DestinationResponseItem>()
    var onItemClick: ((DestinationResponseItem) -> Unit)? = null

    fun setData(newListData: List<DestinationResponseItem>?) {
        if (newListData == null) return
        val diffResult = DiffUtil.calculateDiff(MyDiffUtil(listData, newListData))
        listData.clear()
        listData.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_destination_item, parent, false))
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val binding = CardDestinationItemBinding.bind(itemView)

        fun bind(data: DestinationResponseItem) {
            with(binding){
                Glide.with(itemView.context)
                    .load(data.destinationPhoto)
                    .into(ivDestination)
                tvDestinationName.text = data.nameWisata
                tvDestinationLocation.text = data.city
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }

    }

    class MyDiffUtil(private val oldList: List<DestinationResponseItem>, private val newList: List<DestinationResponseItem>): DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}