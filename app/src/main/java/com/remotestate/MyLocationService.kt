package com.remotestate

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*
import java.util.concurrent.TimeUnit


//..............This service not used as on Android Oreo background Limitations..............//
@Suppress("UNREACHABLE_CODE")
class MyLocationService : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var currentLocation: Location? = null
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        Log.v("Location Service", "On create")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.v("Location Service", "On start command")

        locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(5)
            // Sets the fastest rate for active location updates. This interval is exact, and your
            // application will never receive updates more frequently than this value.
            fastestInterval = TimeUnit.SECONDS.toMillis(5)
            // Sets the maximum time when batched location updates are delivered. Updates may be
            // delivered sooner than this interval.
            maxWaitTime = TimeUnit.MINUTES.toMillis(2)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                Log.v("Location Service", "${location?.latitude} , ${location?.longitude}")
            }

        fusedLocationClient.lastLocation.addOnCanceledListener { }
        fusedLocationClient.lastLocation.addOnCompleteListener { }
        fusedLocationClient.lastLocation.addOnFailureListener { it: Exception? ->
            Log.v("Location Service", it?.message.toString())
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                currentLocation = locationResult.lastLocation
                Log.v("Location Service New", "${currentLocation?.latitude} , ${currentLocation?.longitude}")
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        return START_STICKY

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("Location Service", "onDestroy")

    }
}