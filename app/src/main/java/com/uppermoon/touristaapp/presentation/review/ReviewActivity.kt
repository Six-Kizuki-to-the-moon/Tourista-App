package com.uppermoon.touristaapp.presentation.review

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uppermoon.touristaapp.R
import com.uppermoon.touristaapp.data.dummy.Review
import com.uppermoon.touristaapp.databinding.ActivityReviewBinding
import com.uppermoon.touristaapp.ui.adapter.ListDestinationAdapter
import com.uppermoon.touristaapp.ui.adapter.ListReviewAdapter

class ReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewBinding
    private lateinit var rvReview: RecyclerView
    private val list = ArrayList<Review>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvReview = binding.rvReview
        list.addAll(getReviewList())
        showRecyclerList()

    }

    private fun showRecyclerList() {
        rvReview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val listReviewAdapter = ListReviewAdapter(list)
        rvReview.adapter = listReviewAdapter
    }

    private fun getReviewList(): ArrayList<Review> {
        val dataUsername = resources.getStringArray(R.array.username)
        val dataRating = resources.getStringArray(R.array.user_rating)
        val dataPhoto = resources.getStringArray(R.array.photo_url)
        val dataReview = resources.getStringArray(R.array.review)
        val listReview = ArrayList<Review>()

        for (i in dataUsername.indices) {
            val review = Review(
                dataUsername[i],
                dataRating[i],
                dataPhoto[i],
                dataReview[i]
            )
            listReview.add(review)
        }
        return listReview
    }
}