package com.remotestate

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import java.security.Permission
import java.security.PermissionCollection
import java.security.Permissions

class MainActivity : AppCompatActivity() {

    var isServiceStarted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkLocationPermission()


        findViewById<Button>(R.id.start_stop).setOnClickListener(View.OnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                        arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                        200)
            }

        })

        findViewById<Button>(R.id.goToLogs).setOnClickListener(View.OnClickListener {
            val intent = Intent(MainActivity@ this, LogsActivity::class.java)
            startActivity(intent)
        })
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                Toast.makeText(MainActivity@ this, "Permission granted", Toast.LENGTH_LONG).show()
            }
            else -> {
                // You can directly ask for the permission.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(
                            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                            100)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            100 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                                grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    Toast.makeText(MainActivity@ this, "Location Permission granted", Toast.LENGTH_LONG).show()


                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    Toast.makeText(MainActivity@ this, " location permission is required", Toast.LENGTH_LONG).show()
                }
                return
            }
            200 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                                grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    //Toast.makeText(MainActivity@ this, "Background Permission granted", Toast.LENGTH_LONG).show()

                    startMyService()
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    Toast.makeText(MainActivity@ this, "Background location permission is required", Toast.LENGTH_LONG).show()
                }
                return

            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }


    }

    private fun startMyService() {
        val intent = Intent(MainActivity@ this, ForegroundLocationService::class.java)
        if (!isServiceStarted) {
            isServiceStarted = true
            intent.action = "StartService";
            ContextCompat.startForegroundService(MainActivity@ this, intent);
            findViewById<Button>(R.id.start_stop).text = "Stop Tracking"
        } else {
            isServiceStarted = false
            intent.action = "StopService"
            stopService(intent);
            findViewById<Button>(R.id.start_stop).text = "Start Tracking"
            Toast.makeText(MainActivity@ this, "Service Stopped", Toast.LENGTH_LONG).show()
        }


    }
}