package com.remotestate

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class LogsActivity : AppCompatActivity() {

    lateinit var locationViewModel: LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logs)

        locationViewModel = ViewModelProvider(this)[LocationViewModel::class.java]
        findViewById<RecyclerView>(R.id.locationRecyclerView).layoutManager=LinearLayoutManager(
            LogsActivity@ this
        )
        locationViewModel.allNotes.observe(this, Observer {
            Log.v("Location Service : ", "all notes size : " + it.size)
            val locationAdapter: LocationRecyclerAdapter =
                LocationRecyclerAdapter(LogsActivity@ this, it)

            findViewById<RecyclerView>(R.id.locationRecyclerView).adapter = locationAdapter


        })

    }
}