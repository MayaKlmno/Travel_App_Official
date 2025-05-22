package com.example.travelappofficial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.example.travelappofficial.databinding.ActivityLocationListBinding


class LocationListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLocationListBinding
    private lateinit var adapter: LocationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLocationListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    binding.fabLocationListNewEntry.setOnClickListener{
//        val intent = Intent(this, GameDetailActivity::class.java)
//        startActivity(intent)
    }
    // make backendless call to retrieve all data

    val userId = intent.getStringExtra(LoginActivity.EXTRA_USER_ID)
    val whereClause = "ownerId = '$userId'"
    val queryBuilder = DataQueryBuilder.create()
    queryBuilder.setWhereClause(whereClause)

    Backendless.Data.of(LocationEntry::class.java)
        .find(queryBuilder, object : AsyncCallback<List<LocationEntry>> {
            override fun handleResponse(foundLocationEntry: List<LocationEntry>) {
                // all GameEntry instances have been found
                Log.d("GameListActivity", "handleResponse: $foundLocationEntry")
//                    val gameList = foundGameEntries
                adapter = LocationAdapter(foundLocationEntry.toMutableList())
                binding.recyclerViewLocationListLocations.adapter = adapter
                binding.recyclerViewLocationListLocations.layoutManager =
                    LinearLayoutManager(this@LocationListActivity)
            }

            override fun handleFault(fault: BackendlessFault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Log.d("GameListActivity", "handleFault: ${fault.message}")
            }
        })
    }
}