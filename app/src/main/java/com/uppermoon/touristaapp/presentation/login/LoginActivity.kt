package com.uppermoon.touristaapp.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uppermoon.touristaapp.databinding.ActivityLoginBinding
import com.uppermoon.touristaapp.presentation.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnToRegister = binding.btnGotoRegister


        btnToRegister.setOnClickListener {
            val intentToRegis = Intent(this, RegisterActivity::class.java)
            startActivity(intentToRegis)
        }
    }
}