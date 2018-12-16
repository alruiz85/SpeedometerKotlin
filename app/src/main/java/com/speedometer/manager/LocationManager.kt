package com.speedometer.manager

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.os.Looper
import com.google.android.gms.location.*


class LocationManager(private val context: Context, private val listener: LocationListener) {

    companion object {
        private const val TAG = "LocationManager"

        private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2
    }

    lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var mLocationRequest: LocationRequest
    lateinit var mLocationCallback: LocationCallback
    lateinit var mCurrentLocation: Location

    /**
     * Start location updates.
     */
    fun start() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        createLocationCallback()
        createLocationRequest()
        startLocationUpdates()
    }

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun createLocationCallback() {
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                mCurrentLocation = locationResult.lastLocation
                listener.onLocationChanged(mCurrentLocation)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
    }

    fun stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)

    }

}