package com.example.coffe1706.feature.nearestcoffeeshops.domain

import com.example.coffe1706.core.model.LatLon
import com.example.coffe1706.core.model.response.Response
import com.example.coffe1706.core.model.response.map
import com.example.coffe1706.core.ui.internationalization.formatter.distance.CoarseDistance
import com.example.coffe1706.feature.nearestcoffeeshops.data.NearestLocationsRepository
import com.example.coffe1706.wiring.CoroutinesModule.ComputationDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.math.abs

public interface GetNearestLocationsUseCase {
    suspend fun getNearestLocations(
        location: LatLon,
    ): Response<List<NearestLocation>>
}

class GetNearestLocationsUseCaseImpl @Inject constructor(
    private val nearestLocationsRepository: NearestLocationsRepository,
    @param:ComputationDispatcher private val computationDispatcher: CoroutineContext,
) : GetNearestLocationsUseCase {

    override suspend fun getNearestLocations(
        location: LatLon,
    ): Response<List<NearestLocation>> {
        return nearestLocationsRepository.getNearestLocations(location)
            .map { locations ->
                withContext(computationDispatcher) {
                    locations.map { shop ->
                        val distanceMeters = getDistance(location, shop.position)
                        NearestLocation(
                            id = shop.id,
                            name = shop.name,
                            distance = CoarseDistance.fromMeters(distanceMeters),
                        )
                    }.sortedBy { it.id.id }
                }
            }
    }

    companion object {
        /**
         * WGS84 distance in meters between two [LatLon] points.
         */
        fun getDistance(
            latLon1: LatLon,
            latLon2: LatLon,
        ): Double {
            val distnace = latLon1.toAndroidLocation().distanceTo(latLon2.toAndroidLocation())
            return abs(distnace).toDouble()
        }

        private fun CoarseDistance.Companion.fromMeters(meters: Double): CoarseDistance {
            return if (meters >= 1000) {
                CoarseDistance.kilometers(meters / 1000)
            } else {
                CoarseDistance.meters(meters.toInt())
            }
        }

        private fun LatLon.toAndroidLocation(): android.location.Location {
            val src = this
            return android.location.Location(null).apply {
                latitude = src.latitude
                longitude = src.longitude
            }
        }
    }
}
