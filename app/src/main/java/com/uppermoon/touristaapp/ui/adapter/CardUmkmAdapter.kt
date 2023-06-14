package com.uppermoon.touristaapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uppermoon.touristaapp.data.dummy.Umkm
import com.uppermoon.touristaapp.databinding.CardUmkmItemBinding

class CardUmkmAdapter (private val listUmkm: List<Umkm>) :
    RecyclerView.Adapter<CardUmkmAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CardUmkmItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val dataUmkm = listUmkm[position]
        viewHolder.apply {
            umkmBinding.apply {
                tvUmkmName.text = dataUmkm.umkmName
                tvDestinationLocation.text = dataUmkm.umkmOwner
                Glide.with(itemView.context)
                    .load(dataUmkm.umkmPhoto) // URL Gambar
                    .into(ivUmkm)

            }
        }
    }

    override fun getItemCount(): Int = listUmkm.size

    class ViewHolder(val umkmBinding: CardUmkmItemBinding) :
        RecyclerView.ViewHolder(umkmBinding.root)
}