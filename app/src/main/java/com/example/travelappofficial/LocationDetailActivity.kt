package com.example.travelappofficial

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

        if (locationEntry.emotion == LocationEntry.EMOJI.MOUNTAIN.name){
            binding.imageViewLocationDetailImage.setImageDrawable(getDrawable(R.drawable.mountain_image))
        }
        else if(locationEntry.emotion==(LocationEntry.EMOJI.TROPICAL.name)){
            binding.imageViewLocationDetailImage.setImageDrawable(getDrawable(R.drawable.tropical_image))
        }
        else if(locationEntry.emotion==(LocationEntry.EMOJI.CITY.name)){
            binding.imageViewLocationDetailImage.setImageDrawable(getDrawable(R.drawable.city_image))
        }
        else if(locationEntry.emotion==(LocationEntry.EMOJI.MYSTICAL.name)){
            binding.imageViewLocationDetailImage.setImageDrawable(getDrawable(R.drawable.mystical_image))
        }
        else if(locationEntry.emotion==(LocationEntry.EMOJI.CASTLE.name)){
            binding.imageViewLocationDetailImage.setImageDrawable(getDrawable(R.drawable.castle_image))
        }
        else if(locationEntry.emotion==(LocationEntry.EMOJI.CAMPING.name)){
            binding.imageViewLocationDetailImage.setImageDrawable(getDrawable(R.drawable.camping_image))
        }
        else if(locationEntry.emotion==(LocationEntry.EMOJI.DESERT.name)){
            binding.imageViewLocationDetailImage.setImageDrawable(getDrawable(R.drawable.desert_image))
        }
        else{
            binding.imageViewLocationDetailImage.setImageDrawable(getDrawable(R.drawable.mountain_image))
        }

        binding.textViewLocationDetailLocationName.setText(locationEntry.name)
        binding.textViewLocationDetailCityCountry.setText(locationEntry.city + ", " + locationEntry.country)
        binding.textViewLocationDetailCost.setText("${locationEntry.moneySpent / 100}.${locationEntry.moneySpent % 100}")
        binding.ratingBarLocationDetail.setNumStars(locationEntry.rating)
        binding.textViewLocationDetailDescription.setText(locationEntry.description)



    }
}