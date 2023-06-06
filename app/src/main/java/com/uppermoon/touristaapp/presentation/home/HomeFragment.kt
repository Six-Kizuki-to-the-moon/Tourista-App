package com.uppermoon.touristaapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uppermoon.touristaapp.R
import com.uppermoon.touristaapp.data.dummy.Destination
import com.uppermoon.touristaapp.databinding.FragmentHomeBinding
import com.uppermoon.touristaapp.ui.adapter.ListDestinationAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var rvDestination: RecyclerView
    private val list = ArrayList<Destination>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = FragmentHomeBinding.inflate(inflater, container, false)


        rvDestination = binding.rvPopularItem


        list.addAll(getDestinationList())
        showRecyclerList()

        return binding.root
    }

    private fun showRecyclerList() {
        rvDestination.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val listDestinationAdapter = ListDestinationAdapter(list)
        rvDestination.adapter = listDestinationAdapter
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
}