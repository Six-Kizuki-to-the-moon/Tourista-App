package com.uppermoon.touristaapp.presentation.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import coil.load
import com.uppermoon.touristaapp.R
import com.uppermoon.touristaapp.data.dummy.Destination
import com.uppermoon.touristaapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailDestination = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DETAIL, Destination::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DETAIL)
        }

        if (detailDestination != null) {
            setDetailDestination(detailDestination)
        }
    }

    private fun setDetailDestination(detailDestination: Destination) {
        with(binding){
            tvDestinationName.text = detailDestination.name
            tvDestinationLocation.text = detailDestination.city
            tvDestinationDescription.text = detailDestination.description
            ivImage.load(detailDestination.photo)
        }
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }
}