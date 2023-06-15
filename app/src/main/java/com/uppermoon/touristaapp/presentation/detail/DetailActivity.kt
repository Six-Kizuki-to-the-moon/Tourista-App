package com.uppermoon.touristaapp.presentation.detail

import android.content.Context
import android.content.Intent
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
import com.uppermoon.touristaapp.data.dummy.Similiar
import com.uppermoon.touristaapp.data.dummy.Umkm
import com.uppermoon.touristaapp.data.network.api.ApiConfig
import com.uppermoon.touristaapp.data.network.api.ApiService
import com.uppermoon.touristaapp.data.network.response.DestinationResponseItem
import com.uppermoon.touristaapp.data.preferences.UserPreferences
import com.uppermoon.touristaapp.data.preferences.ViewModelFactory
import com.uppermoon.touristaapp.databinding.ActivityDetailBinding
import com.uppermoon.touristaapp.presentation.review.ReviewActivity
import com.uppermoon.touristaapp.ui.adapter.CardDummySimiliarItemAdapter
import com.uppermoon.touristaapp.ui.adapter.CardSimiliarItemAdapter
import com.uppermoon.touristaapp.ui.adapter.CardUmkmAdapter

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var destinationRepository: DestinationRepository
    private lateinit var adapter : CardSimiliarItemAdapter

    private lateinit var rvUmkm: RecyclerView
    private lateinit var rvSimiliar: RecyclerView
    private val listUmkm = ArrayList<Umkm>()
    private val listSimiliar = ArrayList<Similiar>()

    private lateinit var destinationName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService = ApiConfig.getApiService()
        destinationRepository = DestinationRepository.getInstance(apiService)

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

        val btnToReview = binding.btnGotoReview

        rvUmkm = binding.rvUmkm
        rvSimiliar = binding.rvSimiliarItem
        rvUmkm.setHasFixedSize(true)
        rvSimiliar.setHasFixedSize(true)

        listUmkm.addAll(getListUmkm())
        listSimiliar.addAll(getListSimiliar())
        showRecyclerSimiliarCard()
        showRecyclerUmkmCard()

        if (detailDestination != null) {
            setDetailDestination(detailDestination)
            destinationName = detailDestination.nameWisata
            Log.d("destination", destinationName)

            detailViewModel.postSimiliarItem(destinationName)

//            initRecyclerViewSimiliar()
//            detailViewModel.listSimiliar.observe(this){items ->
//                if (items.isLoading) {
//                }
//                if (items.error.isNotEmpty()) {
//                    Toast.makeText(this, items.error, Toast.LENGTH_SHORT).show()
//                }
//                if (items.similiar.isNotEmpty()) {
//                    adapter.setData(items.similiar)
//                }
//            }
        }

        btnToReview.setOnClickListener {
            val intentReview = Intent(this, ReviewActivity::class.java)
            startActivity(intentReview)
        }
    }

    private fun getListSimiliar(): List<Similiar> {
        val dataSimiliarName = resources.getStringArray(R.array.similiar_name)
        val dataSimiliarCity = resources.getStringArray(R.array.similiar_city)
        val dataSimiliarPhoto = resources.getStringArray(R.array.similiar_photo)

        val list = ArrayList<Similiar>()

        for (i in dataSimiliarName.indices){
            val simi = Similiar(dataSimiliarName[i], dataSimiliarCity[i], dataSimiliarPhoto[i])
            list.add(simi)
        }
        return list
    }

    private fun showRecyclerSimiliarCard() {
        rvSimiliar.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val cardSimiliarAdapter = CardDummySimiliarItemAdapter(listSimiliar)
        rvSimiliar.adapter = cardSimiliarAdapter
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

//    private fun initRecyclerViewSimiliar() {
//        adapter = CardSimiliarItemAdapter()
//        binding.rvSimiliarItem.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        binding.rvSimiliarItem.adapter = adapter
//    }

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