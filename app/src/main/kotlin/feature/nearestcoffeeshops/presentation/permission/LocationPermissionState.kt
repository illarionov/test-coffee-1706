package com.example.coffe1706.feature.nearestcoffeeshops.presentation.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

data class LocationPermissionState(
    val permissionsGranted: Boolean,
    val shouldShowRationale: Boolean,
) {
    public companion object {
        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )

        fun get(
            activity: Activity,
        ): LocationPermissionState {
            val permissionsGranted = areLocationPermissionsGranted(activity)
            return LocationPermissionState(
                permissionsGranted = permissionsGranted,
                shouldShowRationale = if (!permissionsGranted) {
                    shouldShowLocationPermissionRationale(activity)
                } else {
                    false
                },
            )
        }

        private fun shouldShowLocationPermissionRationale(activity: Activity): Boolean = locationPermissions.any {
            ActivityCompat.shouldShowRequestPermissionRationale(activity, it)
        }

        private fun areLocationPermissionsGranted(context: Context): Boolean {
            return locationPermissions.any {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
        }
    }
}
