package com.uppermoon.touristaapp.presentation.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.uppermoon.touristaapp.data.UserResult
import com.uppermoon.touristaapp.data.preferences.UserPreferences
import com.uppermoon.touristaapp.data.preferences.ViewModelFactory
import com.uppermoon.touristaapp.databinding.ActivityLoginBinding
import com.uppermoon.touristaapp.domain.User
import com.uppermoon.touristaapp.presentation.main.MainActivity
import com.uppermoon.touristaapp.presentation.register.RegisterActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var user: User

    private lateinit var email: String
    private lateinit var password: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = UserPreferences.getInstance(dataStore)
        val factory = ViewModelFactory.getInstance(this, pref)
        loginViewModel = ViewModelProvider(this, factory).get(
            LoginViewModel::class.java
        )

        loginViewModel.getToken().observe(this) {
            if (it.token != "token") {
                val intentMain = Intent(this, MainActivity::class.java)
                startActivity(intentMain)
                finish()
            }
        }

        val btnLogin = binding.btnLogin
        val btnToRegister = binding.btnGotoRegister

        btnLogin.setOnClickListener {
            email = binding.etEmail.text.toString()
            password = binding.etPassword.text.toString()
            when {
                email == "" -> {
                    binding.etEmail.error = "email tidak boleh kosong!"
                }
                password == "" -> {
                    binding.etPassword.error = "password tidak boleh kosong!"
                }
                else -> {
                    Log.d("EMAIL", email)
                    Log.d("PW", password)
                    loginViewModel.postLogin(email, password).observe(this) { result ->
                        when (result) {
                            is UserResult.Success -> {
                                showLoading(false)
                                user = User(result.data.accessToken)
                                loginViewModel.saveToken(user)
                                Toast.makeText(this, result.data.msg, Toast.LENGTH_SHORT).show()
                                val intentMain = Intent(this, MainActivity::class.java)
                                startActivity(intentMain)
                                finish()
                            }
                            is UserResult.Loading -> {
                                showLoading(true)
                            }
                            is UserResult.Error -> {
                                Toast.makeText(this, "Login gagal.", Toast.LENGTH_SHORT).show()
                                showLoading(false)
                            }
                        }
                    }

                }
            }
        }

        btnToRegister.setOnClickListener {
            val intentToRegis = Intent(this, RegisterActivity::class.java)
            startActivity(intentToRegis)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}