package com.example.travelappofficial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.travelappofficial.databinding.ActivityRegistrationBinding


class RegistrationActivity : AppCompatActivity(){

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val username = intent.getStringExtra(LoginActivity.EXTRA_USERNAME) ?: ""
        val password = intent.getStringExtra(LoginActivity.EXTRA_PASSWORD) ?: ""

        binding.editTextRegistraionUsername.setText(username)
        binding.editTextPassword.setText(password)

        binding.buttonRegistrationRegister.setOnClickListener {

        }
    }
}