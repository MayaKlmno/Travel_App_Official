package com.example.travelappofficial

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.ActivityResult
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.travelappofficial.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    companion object{
        val EXTRA_USERNAME = "username"
        val EXTRA_PASSWORD = "password"
        val EXTRA_USER_ID = "userid"
    }

    val startRegistrationForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            binding.editTextLoginUsername.setText(intent?.getStringExtra(EXTRA_USERNAME))
            binding.editTextLoginUsername.setText(intent?.getStringExtra(EXTRA_PASSWORD))
        }
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // I added the val Saved Instance State because it was the suggestion given from android studio
        val SavedInstanceState = null
        super.onCreate(SavedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //register with backendless
        Backendless.initApp(this, Constants.APP_ID, Constants.API_KEY)
        binding.buttonLoginLogin.setOnClickListener{
            val username = binding.editTextLoginUsername.text.toString()
            val password = binding.editTextLoginPassword.text.toString()

            Backendless.UserService.login(
                username,
                password,
                object : AsyncCallback<BackendlessUser?> {
                    override fun handleResponse(response: BackendlessUser?) {
                        Toast.makeText(this@LoginActivity,
                            "${user?.userId} has logged in", Toast.LENGTH_LONG). show()
                    }

                    override fun handleFault(fault: BackendlessFault?) {
                        if (fault != null) {
                            Toast.makeText(this@LoginActivity,
                                "${fault.message}", Toast.LENGTH_LONG).show()
                        }
                        if (fault != null) {
                            Log.d("LoginActivity", "handleFault: ${fault.message}")
                        }
                    }
                }
            )
        }
        // laungh the Registration Activity
        binding.textViewLoginSignup.setOnClickListener {
            // this creates an intent object with the current activity and the destination activity's class
            val registrationIntent = Intent(this,
                RegistrationActivity::class.java)
            // optionally add information to send with the intent, key-value pairs just like with Bundles
            registrationIntent.putExtra(EXTRA_USERNAME,
                binding.editTextLoginUsername.text.toString())
            registrationIntent.putExtra(EXTRA_PASSWORD,
                binding.editTextLoginPassword.text.toString())
            // launch the activity for a result using the variable from the register for result contract above
            startRegistrationForResult.launch(registrationIntent)
        }
    }

}