package com.uppermoon.touristaapp.presentation.recommendation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uppermoon.touristaapp.R
import com.uppermoon.touristaapp.data.DestinationRepository
import com.uppermoon.touristaapp.data.dummy.Recresult
import com.uppermoon.touristaapp.data.dummy.Similiar
import com.uppermoon.touristaapp.data.dummy.Umkm
import com.uppermoon.touristaapp.data.network.api.ApiConfig
import com.uppermoon.touristaapp.data.network.response.RecommendationsItem
import com.uppermoon.touristaapp.data.network.response.TripRecommendationResponseItem
import com.uppermoon.touristaapp.data.preferences.UserPreferences
import com.uppermoon.touristaapp.data.preferences.ViewModelFactory
import com.uppermoon.touristaapp.databinding.ActivityRecommendPacketBinding
import com.uppermoon.touristaapp.presentation.home.HomeViewModel
import com.uppermoon.touristaapp.ui.adapter.ListRecommendationAdapter
import com.uppermoon.touristaapp.ui.adapter.ListRecresultAdapter

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
class RecommendPacketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendPacketBinding
    private lateinit var recommendViewModel: RecommendViewModel
    private lateinit var destinationRepository: DestinationRepository
    private lateinit var rvRecresult: RecyclerView
    private val listRecresult = ArrayList<Recresult>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendPacketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val apiService = ApiConfig.getApiService()
        destinationRepository = DestinationRepository.getInstance(apiService)

        val pref = UserPreferences.getInstance(this.dataStore)
        val factory = ViewModelFactory.getInstance(this, destinationRepository, pref)
        recommendViewModel = ViewModelProvider(this, factory).get(
            RecommendViewModel::class.java
        )

        rvRecresult = binding.rvRecommendResult
        rvRecresult.setHasFixedSize(true)

//        recommendViewModel.listDestination.observe(this) { items ->
//            setTripData(items)
//        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvRecommendResult.layoutManager = layoutManager

        recommendViewModel.getRecommendTripDestination(1)
        listRecresult.addAll(getListRecresult())
        showRecyclerRecresult()
    }

    private fun showRecyclerRecresult() {
        rvRecresult.layoutManager = LinearLayoutManager(this)
        val listRecresultAdapter = ListRecresultAdapter(listRecresult)
        rvRecresult.adapter = listRecresultAdapter
    }

    private fun getListRecresult(): List<Recresult> {
        val dataRecresultName = resources.getStringArray(R.array.recresult_name)
        val dataRecresultType = resources.getStringArray(R.array.recresult_type)
        val dataRecresultPhoto = resources.getStringArray(R.array.recresult_photo)

        val list = ArrayList<Recresult>()

        for (i in dataRecresultName.indices){
            val recresult = Recresult(dataRecresultName[i], dataRecresultType[i], dataRecresultPhoto[i])
            list.add(recresult)
        }
        return list
    }

//    override fun onResume() {
//        super.onResume()
//        recommendViewModel.listDestination.observe(this) { items ->
//            setTripData(items)
//        }
//    }
//
//    private fun setTripData(items: List<TripRecommendationResponseItem>) {
//        val adapter = ListRecommendationAdapter(items)
//        binding.rvRecommendResult.adapter = adapter
//    }
}