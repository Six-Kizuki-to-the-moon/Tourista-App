package com.uppermoon.touristaapp.presentation.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.uppermoon.touristaapp.data.DestinationRepository
import com.uppermoon.touristaapp.data.DestinationResult
import com.uppermoon.touristaapp.data.network.api.ApiConfig
import com.uppermoon.touristaapp.data.network.api.ApiService
import com.uppermoon.touristaapp.data.network.response.DestinationResponseItem
import com.uppermoon.touristaapp.data.preferences.UserPreferences
import com.uppermoon.touristaapp.data.preferences.ViewModelFactory
import com.uppermoon.touristaapp.databinding.FragmentHomeBinding
import com.uppermoon.touristaapp.domain.User
import com.uppermoon.touristaapp.ui.adapter.CardDestinationAdapter
import com.uppermoon.touristaapp.ui.adapter.ListDestinationAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var destinationRepository: DestinationRepository
    private lateinit var adapter: CardDestinationAdapter
    private lateinit var secAdapter: ListDestinationAdapter
    private lateinit var user: User
    private lateinit var username: String
    private lateinit var token: String


    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var userLocation: Location? = null

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val apiService = ApiConfig.getApiService()
        destinationRepository = DestinationRepository.getInstance(apiService)

        val pref = UserPreferences.getInstance(requireContext().dataStore)
        val factory = ViewModelFactory.getInstance(requireActivity(), destinationRepository, pref)
        homeViewModel = ViewModelProvider(requireActivity(), factory).get(
            HomeViewModel::class.java
        )

        homeViewModel.getToken().observe(viewLifecycleOwner) {
            user = it
            username = user.username
            token = user.token

            binding.tvUsername.text = username

            Log.d("USERNAME", username)
            Log.d("TOKEN", token)
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        // Mengecek izin lokasi pada saat fragment dibuat
        checkLocationPermission()

        initRecyclerView()

        homeViewModel.listDestination.observe(viewLifecycleOwner) { items ->
            if (items.isLoading) {
            }
            if (items.error.isNotEmpty()) {
                Toast.makeText(requireContext(), items.error, Toast.LENGTH_SHORT).show()
            }
            if (items.destination.isNotEmpty()) {
                adapter.setData(items.destination)
            }
        }

        fetchNearbyData()
        return binding.root
    }

    private fun initRecyclerView() {
        adapter = CardDestinationAdapter()
        binding.rvPopularItem.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopularItem.adapter = adapter
    }


    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Jika izin lokasi belum diberikan, minta izin kepada pengguna
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Jika izin lokasi telah diberikan, dapatkan koordinat lokasi pengguna
            getLastKnownLocation()
        }
    }

    private fun getLastKnownLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task: Task<Location> ->
                if (task.isSuccessful && task.result != null) {
                    userLocation = task.result
                    // Menggunakan koordinat lokasi pengguna untuk panggilan API
                    fetchNearbyData()
                } else {
//                    Snackbar.make(
//                        binding.root,
//                        "Tidak dapat mengambil lokasi pengguna",
//                        Snackbar.LENGTH_SHORT
//                    ).show()
                }
            }
        } else {
            // Izin lokasi belum diberikan oleh pengguna, Anda dapat meminta izin di sini
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun fetchNearbyData() {
        homeViewModel.nearYouDestination(1, -7.8120681F, 110.346218F)
            .observe(viewLifecycleOwner) { result ->
                when (result) {
                    is DestinationResult.Success -> {
                       secAdapter = ListDestinationAdapter(result.data)
                        binding.rvNearItem.layoutManager = LinearLayoutManager(requireActivity())
                        binding.rvNearItem.adapter = secAdapter
                    }
                    is DestinationResult.Loading -> {}
                    is DestinationResult.Error -> {
//                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 123
    }
}