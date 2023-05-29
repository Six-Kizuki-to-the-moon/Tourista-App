package com.uppermoon.touristaapp.presentation.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uppermoon.touristaapp.R
import com.uppermoon.touristaapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}