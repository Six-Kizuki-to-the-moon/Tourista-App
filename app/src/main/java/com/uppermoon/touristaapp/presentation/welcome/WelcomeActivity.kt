package com.uppermoon.touristaapp.presentation.welcome

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.uppermoon.touristaapp.R
import com.uppermoon.touristaapp.data.preferences.UserPreferences
import com.uppermoon.touristaapp.data.preferences.ViewModelFactory
import com.uppermoon.touristaapp.databinding.ActivityWelcomeBinding
import com.uppermoon.touristaapp.domain.User
import com.uppermoon.touristaapp.presentation.login.LoginActivity
import com.uppermoon.touristaapp.presentation.login.LoginViewModel
import com.uppermoon.touristaapp.presentation.main.MainActivity
import kotlinx.coroutines.flow.first

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var welcomeViewModel: WelcomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val pref = UserPreferences.getInstance(dataStore)
        //val loggedIn = pref.isLoggedIn().asLiveData() // Mengambil status login dari DataStore
        val factory = ViewModelFactory.getInstance(this, pref)
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

//        loggedIn.observe(this) { isLoggedIn ->
//            if (isLoggedIn) {
//                startMainActivity() // Pengguna sudah login, langsung buka MainActivity
//            } else {
//                showWelcomeScreen() // Pengguna belum login, tampilkan WelcomeActivity
//            }
//        }
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