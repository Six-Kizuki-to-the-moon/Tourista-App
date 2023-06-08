package com.uppermoon.touristaapp.presentation.review

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.uppermoon.touristaapp.R
import com.uppermoon.touristaapp.data.dummy.Review
import com.uppermoon.touristaapp.databinding.ActivityReviewBinding

class ReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewBinding
    private lateinit var rvReview: RecyclerView
    private val list = ArrayList<Review>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}