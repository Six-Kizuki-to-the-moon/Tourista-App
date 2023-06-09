package com.uppermoon.touristaapp.presentation.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.uppermoon.touristaapp.R
import com.uppermoon.touristaapp.data.dummy.Destination
import com.uppermoon.touristaapp.databinding.FragmentHomeBinding
import com.uppermoon.touristaapp.ui.adapter.CardDestinationAdapter
import com.uppermoon.touristaapp.ui.adapter.ListDestinationAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var rvCardDestination: RecyclerView
    private lateinit var rvListDestination: RecyclerView
    private val list = ArrayList<Destination>()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var userLocation: Location? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = FragmentHomeBinding.inflate(inflater, container, false)


        rvCardDestination = binding.rvPopularItem
        rvListDestination = binding.rvNearItem

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        // Mengecek izin lokasi pada saat fragment dibuat
        checkLocationPermission()

        list.addAll(getDestinationList())
        showRecyclerCard()
        showRecyclerList()

        return binding.root
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
                    Snackbar.make(
                        binding.root,
                        "Tidak dapat mengambil lokasi pengguna",
                        Snackbar.LENGTH_SHORT
                    ).show()
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
        // Panggil API dengan menggunakan userLocation untuk mendapatkan data terdekat
        // Implementasikan metode untuk memanggil API dan menampilkan responsnya di RecyclerView
    }


    private fun showRecyclerCard() {
        rvCardDestination.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val cardDestinationAdapter = CardDestinationAdapter(list)
        rvCardDestination.adapter = cardDestinationAdapter
    }

    private fun showRecyclerList() {
        rvListDestination.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val listDestinationAdapter = ListDestinationAdapter(list)
        rvListDestination.adapter = listDestinationAdapter
    }

    private fun getDestinationList(): ArrayList<Destination> {
        val dataDestinationName = resources.getStringArray(R.array.place_name)
        val dataDestinationCity = resources.getStringArray(R.array.place_city)
        val dataDestinationPhoto = resources.getStringArray(R.array.photo_city)
        val dataDescription = resources.getStringArray(R.array.description_city)
        val listDestination = ArrayList<Destination>()

        for (i in dataDestinationName.indices) {
            val destination = Destination(
                dataDestinationName[i],
                dataDestinationCity[i],
                dataDestinationPhoto[i],
                dataDescription[i]
            )
            listDestination.add(destination)
        }
        return listDestination
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 123
    }
}