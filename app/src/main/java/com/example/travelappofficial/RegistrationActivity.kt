package com.example.travelappofficial

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
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
            val password = binding.editTextTextPassword.text.toString()
            val confirm = binding.editTextRegistrationConformPassword.text.toString()
            val username = binding.editTextRegistrationUsername.text.toString()
            val name = binding.editTextRegistrationName.text.toString()
            val email = binding.editTextRegistrationEmail.text.toString()

            if(password == confirm){
                val user = BackendlessUser()
                user.setProperty("email", email)
                user.setProperty("username", username)
                user.password = password

                Backendless.UserService.register(user, object: AsyncCallback<BackendlessUser>?) {
                    override fun handleResponse(response: BackendlessUser?){
                        val intent = Intent()
                        intent.putExtra(LoginActivity.EXTRA_USERNAME, username)
                        intent.putExtra(LoginActivity.EXTRA_PASSWORD, password)
                        setResult(RESULT_OK, intent)
                        finish()
                        // after this the user has been registered and can now login
                    }
                }
            }

        }
    }
}