package com.example.myfashion.presentasion.home

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices


class LocationHelper(context: Context) {

    private val fusedClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        onLocation: (lat: Double, lon: Double) -> Unit,
        onError: () -> Unit
    ) {
        fusedClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    onLocation(location.latitude, location.longitude)
                } else {
                    onError()
                }
            }
            .addOnFailureListener {
                onError()
            }
    }
}
