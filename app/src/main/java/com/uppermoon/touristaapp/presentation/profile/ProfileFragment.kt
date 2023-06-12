package com.uppermoon.touristaapp.presentation.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.uppermoon.touristaapp.R
import com.uppermoon.touristaapp.data.DestinationRepository
import com.uppermoon.touristaapp.data.network.api.ApiConfig
import com.uppermoon.touristaapp.data.preferences.UserPreferences
import com.uppermoon.touristaapp.data.preferences.ViewModelFactory
import com.uppermoon.touristaapp.databinding.FragmentProfileBinding
import com.uppermoon.touristaapp.presentation.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var destinationRepository: DestinationRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val apiService = ApiConfig.getApiService()
        val userPreferences = requireContext().dataStore
        destinationRepository = DestinationRepository.getInstance(apiService)
        val instance = UserPreferences.getInstance(userPreferences)
        profileViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireContext(), destinationRepository, instance)).get(ProfileViewModel::class.java)

        val btnLogout = binding.btnGotoLogin
        val btnCreateProfile = binding.btnCreateProfile

        btnLogout.setOnClickListener {
            logoutUser()
        }

        btnCreateProfile.setOnClickListener {
            navigateToCreateProfile()
        }
        return root
    }

    private fun navigateToCreateProfile() {
        val intentCreateProfile = Intent(requireContext(), CreateProfileActivity::class.java)
        startActivity(intentCreateProfile)
    }

    private fun logoutUser() {
        profileViewModel.clearToken()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()

    }
}