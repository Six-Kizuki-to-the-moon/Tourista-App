package com.uppermoon.touristaapp.presentation.explore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.uppermoon.touristaapp.data.DestinationRepository
import com.uppermoon.touristaapp.data.DestinationResult
import com.uppermoon.touristaapp.data.network.api.ApiConfig
import com.uppermoon.touristaapp.data.preferences.UserPreferences
import com.uppermoon.touristaapp.data.preferences.ViewModelFactory
import com.uppermoon.touristaapp.databinding.FragmentExploreBinding
import com.uppermoon.touristaapp.domain.User
import com.uppermoon.touristaapp.presentation.recommendation.RecommendPacketActivity

class ExploreFragment : Fragment() {

    private lateinit var binding: FragmentExploreBinding
    private lateinit var exploreViewModel: ExploreViewModel
    private lateinit var destinationRepository: DestinationRepository
    private lateinit var user: User
    private lateinit var token: String

    private var selectedCity: String? = null
    private var selectedCategory: String? = null

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)

        val cityOptions = arrayOf("Bandung", "Jakarta", "Yogyakarta", "Semarang", "Surabaya")
        val cityAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cityOptions)
        binding.spinnerCity.adapter = cityAdapter

        val categoryOptions = arrayOf("Taman Hiburan", "Budaya", "Cagar Alam", "Bahari", "Pusat Perbelanjaan", "Tempat Ibadah")
        val categoryAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryOptions)
        binding.spinnerCategories.adapter = categoryAdapter

        val apiService = ApiConfig.getApiService()
        destinationRepository = DestinationRepository.getInstance(apiService)

        val pref = UserPreferences.getInstance(requireContext().dataStore)
        val factory = ViewModelFactory.getInstance(requireActivity(), destinationRepository, pref)
        exploreViewModel = ViewModelProvider(requireActivity(), factory).get(
            ExploreViewModel::class.java
        )

        exploreViewModel.getToken().observe(viewLifecycleOwner){
            user = it
            token = user.token
        }


        binding.spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCity = cityOptions[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedCity = null
            }
        }

        binding.spinnerCategories.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedCategory = categoryOptions[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    selectedCategory = null
                }
            }

        binding.btnSearch.setOnClickListener {
            val priceText = binding.etPrice.text.toString().trim()
            if (selectedCity.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Please select a city", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedCategory.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Please select a category", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (priceText.isEmpty()) {
                Toast.makeText(requireContext(), "Please input a budget", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val price = priceText.toIntOrNull()
            if (price == null) {
                Toast.makeText(requireContext(), "Invalid price input", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("CITY", selectedCity.toString())
            Log.d("CATEGORY", selectedCategory.toString())
            Log.d("PRICE", price.toString())

            exploreViewModel.postUserRecommendation(1, selectedCity!!, selectedCategory!!, price).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is DestinationResult.Success -> {
                        Toast.makeText(requireContext(), result.data.status, Toast.LENGTH_SHORT).show()
                        val intent = Intent(requireContext(), RecommendPacketActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                    is DestinationResult.Loading -> {

                    }
                    is DestinationResult.Error -> {
                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
        return binding.root
    }
}