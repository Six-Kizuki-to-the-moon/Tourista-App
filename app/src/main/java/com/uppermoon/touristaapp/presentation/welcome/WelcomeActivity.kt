package com.uppermoon.touristaapp.presentation.welcome

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.uppermoon.touristaapp.data.DestinationRepository
import com.uppermoon.touristaapp.data.network.api.ApiConfig
import com.uppermoon.touristaapp.data.network.api.ApiService
import com.uppermoon.touristaapp.data.preferences.UserPreferences
import com.uppermoon.touristaapp.data.preferences.ViewModelFactory
import com.uppermoon.touristaapp.databinding.ActivityWelcomeBinding
import com.uppermoon.touristaapp.presentation.login.LoginActivity
import com.uppermoon.touristaapp.presentation.main.MainActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var welcomeViewModel: WelcomeViewModel
    private lateinit var destinationRepository: DestinationRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val apiService = ApiConfig.getApiService()
        destinationRepository = DestinationRepository.getInstance(apiService)

        val pref = UserPreferences.getInstance(dataStore)
        val factory = ViewModelFactory.getInstance(this, destinationRepository, pref)
        welcomeViewModel = ViewModelProvider(this, factory).get(
            WelcomeViewModel::class.java
        )

        welcomeViewModel.getToken().observe(this) {
            if (it.token != "token") {
                startMainActivity()
            } else {
                showWelcomeScreen()
            }
        }
    }

    private fun showWelcomeScreen() {
        supportActionBar?.hide()

        binding.btnWelcome.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}