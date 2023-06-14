package com.uppermoon.touristaapp.presentation.recommendation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uppermoon.touristaapp.R
import com.uppermoon.touristaapp.databinding.ActivityRecommendPacketBinding

class RecommendPacketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendPacketBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend_packet)
    }
}