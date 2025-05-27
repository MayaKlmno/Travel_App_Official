package com.example.travelappofficial

import android.R
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.travelappofficial.databinding.ActivityLocationDetailBinding


class LocationDetailActivity : AppCompatActivity() {
    companion object {
        val TAG = "LocationDetailActivity"
        val EXTRA_LOCATION_ENTRY = "location entry"
    }

    private lateinit var binding: ActivityLocationDetailBinding
    private lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLocationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val locationEntry = intent.getParcelableExtra<LocationEntry>(EXTRA_LOCATION_ENTRY) ?: LocationEntry()

        binding.textViewLocationDetailLocationName.setText(locationEntry.name)
        binding.textViewLocationDetailCityCountry.setText(locationEntry.location)
        binding.textViewLocationDetailCost.setText("${locationEntry.moneySpent / 100}.${locationEntry.moneySpent % 100}")
        binding.ratingBarLocationDetail.setNumStars(locationEntry.rating)
        binding.textViewLocationDetailDescription.setText(locationEntry.description)

//FOR THIS PART MAKE EMOJI INTO BACKGROUND PIC
        if (locationEntry.emotion.equals(LocationEntry.EMOJI.MOUNTAIN.name)){

        }
//        var spinnerItems = LocationEntry.EMOJI.entries.map { it.emoji }
//        binding.spinnerGameDetailEmotion.adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, spinnerItems)
//        var position = spinnerItems.indexOf(locationEntry.emotion)
//        if(position < 0) {
//            position = 0
//        }
//        binding.spinnerGameDetailEmotion.setSelection(position)
    }
}