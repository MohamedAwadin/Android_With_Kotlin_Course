package com.example.aklab2_01

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var latitudeValueText: TextView
    private lateinit var longitudeValueText: TextView
    private lateinit var addressValueText: TextView
    private lateinit var openSmsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        latitudeValueText = findViewById(R.id.latitudeValue)
        longitudeValueText = findViewById(R.id.longitudeValue)
        addressValueText = findViewById(R.id.addressValue)
        openSmsButton = findViewById(R.id.openSmsButton)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Set click listener for the Open SMS button
        openSmsButton.setOnClickListener {
            val address = addressValueText.text.toString()
            if (address != "value" && address != "Not available" && address != "Geocoding failed" && address != "Permission denied" && address != "Error") {
                openSMS(address)
            } else {
                addressValueText.text = "Get location first"
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            getLastLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLastLocation()
        } else {
            latitudeValueText.text = "Permission denied"
            longitudeValueText.text = "Permission denied"
            addressValueText.text = "Permission denied"
        }
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                location?.let {
                    val latitude = it.latitude
                    val longitude = it.longitude
                    latitudeValueText.text = latitude.toString()
                    longitudeValueText.text = longitude.toString()

                    // Use Geocoder to get the address
                    val geocoder = Geocoder(this, Locale.getDefault())
                    try {
                        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                        if (!addresses.isNullOrEmpty()) {
                            val address = addresses[0]
                            addressValueText.text = address.getAddressLine(0) ?: "Address not found"
                        } else {
                            addressValueText.text = "Address not found"
                        }
                    } catch (e: Exception) {
                        addressValueText.text = "Geocoding failed"
                    }
                } ?: run {
                    latitudeValueText.text = "Not available"
                    longitudeValueText.text = "Not available"
                    addressValueText.text = "Not available"
                }
            }
            .addOnFailureListener {
                latitudeValueText.text = "Error"
                longitudeValueText.text = "Error"
                addressValueText.text = "Error"
            }
    }

    private fun openSMS(address: String) {
        val phoneNumber = "+201028785711"
        val smsIntent = Intent(Intent.ACTION_VIEW).apply {
            data = android.net.Uri.parse("smsto:$phoneNumber")
            putExtra("sms_body", address)

        }
        try {
            startActivity(smsIntent)
        } catch (e: android.content.ActivityNotFoundException) {
            addressValueText.text = "No SMS app found"
        }
    }
}