package com.uppermoon.touristaapp.presentation.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.uppermoon.touristaapp.databinding.FragmentExploreBinding

class ExploreFragment : Fragment() {

    private lateinit var binding: FragmentExploreBinding

    private var selectedCity: String? = null
    private var selectedCategory: String? = null
    private lateinit var price: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)

        val cityOptions = arrayOf("Bandung", "Jakarta", "Yogyakarta", "Semarang", "Surabaya")
        val cityAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cityOptions)
        binding.spinnerCity.adapter = cityAdapter

        val categoryOptions = arrayOf("Atraksi", "Taman Hiburan", "Petualangan")
        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryOptions)
        binding.spinnerCategories.adapter = categoryAdapter

        binding.spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCity = cityOptions[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedCity = null
            }
        }

        binding.spinnerCategories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCategory = categoryOptions[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedCategory = null
            }
        }

        binding.btnSearch.setOnClickListener {
            price = binding.etPrice.toString()
            when {
                selectedCity.isNullOrEmpty() -> {
                    // Menampilkan pesan error untuk Spinner City
                    Toast.makeText(requireContext(), "Please select a city", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                selectedCategory.isNullOrEmpty() -> {
                    // Menampilkan pesan error untuk Spinner Category
                    Toast.makeText(requireContext(), "Please select a category", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                price == "" -> {
                    // Menampilkan pesan error untuk Spinner Category
                    Toast.makeText(requireContext(), "Please input a budget", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                else -> {
                    // Lanjutkan dengan tindakan yang diinginkan setelah validasi berhasil
                    // ...
                }


            }

        }
        return binding.root
    }
}