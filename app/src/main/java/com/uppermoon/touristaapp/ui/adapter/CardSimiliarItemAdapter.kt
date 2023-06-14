package com.uppermoon.touristaapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uppermoon.touristaapp.R
import com.uppermoon.touristaapp.data.network.response.RecommendationsItem
import com.uppermoon.touristaapp.databinding.CardDestinationItemBinding

class CardSimiliarItemAdapter : RecyclerView.Adapter<CardSimiliarItemAdapter.ViewHolder>(){

    private var listSimiliar = ArrayList<RecommendationsItem>()
    var onItemClick: ((RecommendationsItem) -> Unit)? = null

    fun setData(newListData: List<RecommendationsItem>?) {
        if (newListData == null) return
        val diffResult = DiffUtil.calculateDiff(MyDiffUtil(
                listSimiliar, newListData
            )
        )
        listSimiliar.clear()
        listSimiliar.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardSimiliarItemAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_destination_item, parent, false))
    }

    override fun getItemCount(): Int = listSimiliar.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
       val data = listSimiliar[position]
        viewHolder.bind(data)
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val binding = CardDestinationItemBinding.bind(itemView)

        fun bind(data: RecommendationsItem) {
            with(binding){
                Glide.with(itemView.context)
                    .load(data.destinationPhoto)
                    .into(ivDestination)
                tvDestinationName.text = data.placeName
                tvDestinationLocation.text = data.city
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listSimiliar[adapterPosition])
            }
        }

    }

    class MyDiffUtil(private val oldList: List<RecommendationsItem>, private val newList: List<RecommendationsItem>): DiffUtil.Callback() {
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