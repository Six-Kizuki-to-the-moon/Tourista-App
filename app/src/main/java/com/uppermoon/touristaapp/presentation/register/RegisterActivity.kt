package com.uppermoon.touristaapp.presentation.register

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.uppermoon.touristaapp.R
import com.uppermoon.touristaapp.data.UserResult
import com.uppermoon.touristaapp.data.preferences.UserPreferences
import com.uppermoon.touristaapp.data.preferences.ViewModelFactory
import com.uppermoon.touristaapp.databinding.ActivityRegisterBinding
import com.uppermoon.touristaapp.presentation.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    private lateinit var username: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var confirmPassword: String

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnRegister = binding.btnRegister

        val pref = UserPreferences.getInstance(dataStore)
        val factory = ViewModelFactory.getInstance(this, pref)
        registerViewModel = ViewModelProvider(this, factory).get(
            RegisterViewModel::class.java
        )

        btnRegister.setOnClickListener {
            username = binding.etUsername.text.toString()
            email = binding.etEmail.text.toString()
            password = binding.etPassword.text.toString()
            confirmPassword = binding.etConfirmPassword.text.toString()
            when {
                username == "" -> {
                    binding.etUsername.error = "nama tidak boleh kosong!"
                }
                email == "" -> {
                    binding.etEmail.error = "email tidak boleh kosong!"
                }
                password == "" -> {
                    binding.etPassword.error = "password tidak boleh kosong!"
                }
                confirmPassword == "" -> {
                    binding.etConfirmPassword.error = "password tidak boleh kosong!"
                }
                else -> {
                    userRegister()
                }
            }
        }
    }

    private fun userRegister() {
        registerViewModel.postRegister(
            username, email, password, confirmPassword
        ).observe(this) { result ->
            when (result) {
                is UserResult.Success -> {
                    showLoading(false)
                    Toast.makeText(this, "Akun berhasil dibuat", Toast.LENGTH_SHORT).show()
                    val intentLogin = Intent(this, LoginActivity::class.java)
                    startActivity(intentLogin)
                }
                is UserResult.Loading -> {
                    showLoading(true)
                }
                is UserResult.Error -> {
                    Toast.makeText(this, "Akun gagal dibuat", Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}