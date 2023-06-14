package com.uppermoon.touristaapp.presentation.detail

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.uppermoon.touristaapp.R
import com.uppermoon.touristaapp.data.DestinationRepository
import com.uppermoon.touristaapp.data.DestinationResult
import com.uppermoon.touristaapp.data.dummy.Umkm
import com.uppermoon.touristaapp.data.network.api.ApiConfig
import com.uppermoon.touristaapp.data.network.api.ApiService
import com.uppermoon.touristaapp.data.network.response.DestinationResponseItem
import com.uppermoon.touristaapp.data.preferences.UserPreferences
import com.uppermoon.touristaapp.data.preferences.ViewModelFactory
import com.uppermoon.touristaapp.databinding.ActivityDetailBinding
import com.uppermoon.touristaapp.ui.adapter.CardSimiliarItemAdapter
import com.uppermoon.touristaapp.ui.adapter.CardUmkmAdapter

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var destinationRepository: DestinationRepository
    private lateinit var moduleApiService: ApiService
    private lateinit var adapter : CardSimiliarItemAdapter

    private lateinit var rvUmkm: RecyclerView
    private val listUmkm = ArrayList<Umkm>()

    private lateinit var destinationName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moduleApiService = ApiConfig.getApiService2()
        destinationRepository = DestinationRepository.getInstance(moduleApiService)

        val pref = UserPreferences.getInstance(this.dataStore)
        val factory = ViewModelFactory.getInstance(this, destinationRepository, pref)
        detailViewModel = ViewModelProvider(this, factory).get(
            DetailViewModel::class.java
        )

        val detailDestination = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DETAIL, DestinationResponseItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DETAIL)
        }

        rvUmkm = binding.rvUmkm
        rvUmkm.setHasFixedSize(true)

        listUmkm.addAll(getListUmkm())
        showRecyclerUmkmCard()

        if (detailDestination != null) {
            setDetailDestination(detailDestination)
            destinationName = detailDestination.city

            initRecyclerViewSimiliar()

            detailViewModel.listSimiliar.observe(this) { items ->
                if (items.isLoading) {
                }
                if (items.error.isNotEmpty()) {
                    Toast.makeText(this, items.error, Toast.LENGTH_SHORT).show()
                }
                if (items.similiar.isNotEmpty()) {
                    adapter.setData(items.similiar)
                    Log.d("data", items.similiar.toString())
                }
            }
        }
    }

    private fun getListUmkm(): List<Umkm> {
        val dataUmkmName = resources.getStringArray(R.array.umkm_name)
        val dataUmkmOwner = resources.getStringArray(R.array.umkm_owner)
        val dataUmkmPhoto = resources.getStringArray(R.array.umkm_photo)

        val list = ArrayList<Umkm>()

        for (i in dataUmkmName.indices){
            val city = Umkm(dataUmkmName[i], dataUmkmOwner[i], dataUmkmPhoto[i])
            list.add(city)
        }
        return list
    }

    private fun showRecyclerUmkmCard() {
        rvUmkm.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val cardUmkmAdapter = CardUmkmAdapter(listUmkm)
        rvUmkm.adapter = cardUmkmAdapter
    }

    private fun initRecyclerViewSimiliar() {
        adapter = CardSimiliarItemAdapter()
        binding.rvSimiliarItem.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvSimiliarItem.adapter = adapter

    }
    private fun setDetailDestination(detailDestination: DestinationResponseItem) {
        with(binding) {
            tvDestinationName.text = detailDestination.nameWisata
            tvDestinationLocation.text = detailDestination.city
            tvDestinationDescription.text = detailDestination.descriptionWisata
            ivImage.load(detailDestination.destinationPhoto)
        }
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }
}