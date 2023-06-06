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
import com.uppermoon.touristaapp.data.preferences.UserPreferences
import com.uppermoon.touristaapp.data.preferences.ViewModelFactory
import com.uppermoon.touristaapp.databinding.FragmentProfileBinding
import com.uppermoon.touristaapp.presentation.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var username: String
    private lateinit var email: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val btnLogout = binding.btnGotoLogin

        btnLogout.setOnClickListener {
            logoutUser()
        }
        return root
    }

    private fun logoutUser() {
        val userPreferences = requireContext().dataStore
        val instance = UserPreferences.getInstance(userPreferences)
        val profileViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireContext(), instance)).get(ProfileViewModel::class.java)

        profileViewModel.clearToken()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()

    }
}