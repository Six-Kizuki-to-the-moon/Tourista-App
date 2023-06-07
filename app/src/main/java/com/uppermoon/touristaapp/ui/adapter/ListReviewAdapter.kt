package com.uppermoon.touristaapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uppermoon.touristaapp.data.dummy.Review
import com.uppermoon.touristaapp.databinding.ListReviewItemBinding

class ListReviewAdapter(private var listReview: ArrayList<Review>) :
    RecyclerView.Adapter<ListReviewAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ListViewHolder, position: Int) {
       val dataReview = listReview[position]
        viewHolder.apply {
            reviewItemBinding.apply {
                tvUsernameLabel.text = dataReview.username
                tvRating.text = dataReview.rating.toString()
                tvReview.text = dataReview.review
            }
        }
    }

    override fun getItemCount(): Int = listReview.size

    class ListViewHolder(val reviewItemBinding: ListReviewItemBinding): RecyclerView.ViewHolder (reviewItemBinding.root)
}