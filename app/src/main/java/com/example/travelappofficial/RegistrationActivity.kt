package com.example.travelappofficial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.travelappofficial.databinding.ActivityRegistrationBinding


class RegistrationActivity : AppCompatActivity(){

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(LoginActivity.EXTRA_USERNAME) ?: ""
        val password = intent.getStringExtra(LoginActivity.EXTRA_PASSWORD) ?: ""

        binding.editTextRegistrationUsername.setText(username)
        binding.editTextRegistrationPassword.setText(password)

        binding.buttonRegistrationSignUp.setOnClickListener {
            val password = binding.editTextRegistrationPassword.text.toString()
            val confirm = binding.editTextRegistrationConfirmPassword.text.toString()
            val username = binding.editTextRegistrationUsername.text.toString()
            val name = binding.editTextRegistrationName.text.toString()
            val email = binding.editTextRegistrationEmail.text.toString()

            if(password == confirm){
                val user = BackendlessUser()
                user.setProperty("email", email)
                user.setProperty("name", name)
                user.setProperty("username", username)
                user.password = password

                Backendless.UserService.register(user, object : AsyncCallback<BackendlessUser?> {
                    override fun handleResponse(registeredUser: BackendlessUser?) {
                        // user has been registered and now can login
                        val intent = Intent()
                        intent.putExtra(LoginActivity.EXTRA_USERNAME, username)
                        intent.putExtra(LoginActivity.EXTRA_PASSWORD,password)
                        setResult(RESULT_OK, intent)
                        finish()
                    }

                    override fun handleFault(fault: BackendlessFault) {
                        // an error has occurred, the error code can be retrieved with fault.getCode()
                        Log.d("Registration Activity", "handleFault: ${fault.message}")
                    }
                })
            }

        }
    }
}