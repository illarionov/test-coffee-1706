package com.example.coffe1706.data.location

import android.annotation.SuppressLint
import android.content.Context
import com.example.coffe1706.core.model.LatLon
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import android.location.Location as AndroidLocation

public interface CurrentLocationDataSource {
    public fun getCurrentLocationFlow(): Flow<LocationResult>

    public sealed interface LocationResult {
        public data class Success(
            val coordinates: LatLon,
        ) : LocationResult

        public sealed class Failure : LocationResult {
            public object NoLocationPermissionError : Failure()
            public data class OtherError(val error: Throwable) : Failure()
        }
    }
}

private const val DEFAULT_LOCATION_UPDATE_PERIOD = 5000L

/**
 * Реализация [CurrentLocationDataSource] на основе [FusedLocationProviderClient].
 *
 * Требует, чтобы на момент вызова [getCurrentLocationFlow] разрешение на местоположение было уже получено
 * от пользователя.
 */
public class FusedCurrentLocationDataSource(
    context: Context,
) : CurrentLocationDataSource {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override fun getCurrentLocationFlow(): Flow<CurrentLocationDataSource.LocationResult> = channelFlow {
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                if (result.locations.isNotEmpty()) {
                    val last: AndroidLocation = result.locations.maxBy { it.time }
                    trySend(
                        CurrentLocationDataSource.LocationResult.Success(
                            LatLon(
                                latitude = last.latitude,
                                longitude = last.longitude,
                            ),
                        ),
                    )
                }
            }

            override fun onLocationAvailability(availability: LocationAvailability) {
                if (!availability.isLocationAvailable) {
                    trySend(
                        CurrentLocationDataSource.LocationResult.Failure.OtherError(
                            error = Exception("Location unavailable"),
                        ),
                    )
                }
            }
        }

        try {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                trySend(
                    CurrentLocationDataSource.LocationResult.Success(
                        LatLon(
                            latitude = it.latitude,
                            longitude = it.longitude,
                        ),
                    ),
                )
            }

            val locationRequest = LocationRequest.Builder(DEFAULT_LOCATION_UPDATE_PERIOD)
                .setMinUpdateDistanceMeters(100f)
                .build()

            fusedLocationClient.requestLocationUpdates(locationRequest, callback, null)
        } catch (_: SecurityException) {
            trySend(
                CurrentLocationDataSource.LocationResult.Failure.NoLocationPermissionError,
            )
        }

        awaitClose {
            fusedLocationClient.removeLocationUpdates(callback)
        }
    }
}
