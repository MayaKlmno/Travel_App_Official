package com.example.travelappofficial

import android.R
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.travelappofficial.databinding.ActivityLocationNewBinding

class LocationNewActivity : AppCompatActivity() {
    companion object {
        val TAG = "LocationNewActivity"
        val EXTRA_LOCATION_ENTRY = "location entry"
    }
    private lateinit var binding: ActivityLocationNewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLocationNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val locationEntry = intent.getParcelableExtra<LocationEntry>(EXTRA_LOCATION_ENTRY) ?: LocationEntry()

        binding.editTextLocationNewLocationNameEntry.setText(locationEntry.name)
        binding.editTextLocationNewPriceEntry.setText("${locationEntry.moneySpent/100}.${locationEntry.moneySpent%100}")
        binding.editTextLocationNewCountryEntry.setText(locationEntry.country)
        binding.editTextLocationNewCityEntry.setText(locationEntry.city)
        var spinnerItems = LocationEntry.EMOJI.entries.map { it.emoji }
        binding.spinnerLocationNewEmoji.adapter =
            ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, spinnerItems)
        var position = spinnerItems.indexOf(locationEntry.emotion)
        if(position < 0) {
            position = 0
        }
        binding.spinnerLocationNewEmoji.setSelection(position)

        binding.buttonLocationNewSave.setOnClickListener {
            locationEntry.ownerId = Backendless.UserService.CurrentUser().userId
            locationEntry.name = binding.editTextLocationNewLocationNameEntry.text.toString()
            // for money spent, remove the leading $
            val moneyString = binding.editTextLocationNewPriceEntry.text.toString()
            var cents = 0
            if(!moneyString.contains(".")) {
                cents = moneyString.toInt() * 100
            } else {
                var dollarsAndCents = moneyString.split(".")
                var dollarString = dollarsAndCents[0]
                var centsString = dollarsAndCents[1]
                if(dollarString.isNotEmpty()) {
                    cents += dollarString.toInt() * 100
                }
                if(centsString.isNotEmpty()) {
                    if(centsString.length > 2) {
                        centsString = centsString.substring(0,2)
                    } else if(centsString.length == 1) {
                        centsString += "0"
                    }
                    cents += centsString.toInt()
                }
            }
            locationEntry.moneySpent = cents
            locationEntry.emotion =  LocationEntry.EMOJI.entries.find { it.emoji == binding.spinnerLocationNewEmoji.selectedItem.toString()}!!.name
            saveToBackendless(locationEntry)

        }

    }

    private fun saveToBackendless(locationEntry: LocationEntry) {
        // code here to save to backendless
        Backendless.Data.of(LocationEntry::class.java).save(locationEntry, object : AsyncCallback<LocationEntry?> {
            override fun handleResponse(response: LocationEntry?) {
                Toast.makeText(this@LocationNewActivity, "${locationEntry} has been successfully updated", Toast.LENGTH_SHORT).show()
            }

            override fun handleFault(fault: BackendlessFault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Log.d("LocationNewActivity", "handleFault: ${fault.message}")
            }
        })
    }
}