package com.example.coffe1706.feature.nearestcoffeeshops.data

import com.example.coffe1706.core.model.LatLon
import com.example.coffe1706.core.model.Location
import com.example.coffe1706.core.model.response.Response
import com.example.coffe1706.core.model.response.isUnauthorized
import com.example.coffe1706.data.coffee1706api.Coffee1706NetworkDataSource
import com.example.coffe1706.data.coffee1706api.session.Coffee1706SessionDataSource
import javax.inject.Inject

internal interface NearestLocationsRepository {
    suspend fun getNearestLocations(location: LatLon?): Response<List<Location>>
}

public class NearestLocationsRepositoryImpl @Inject constructor (
    private val nearestCoffeeShopsDataSource: Coffee1706NetworkDataSource,
    private val sessionDataSource: Coffee1706SessionDataSource,
) : NearestLocationsRepository {
    override suspend fun getNearestLocations(location: LatLon?): Response<List<Location>> {
        // XXX сервер не учитывает передаваемое текущее местоположение
        val response: Response<List<Location>> =  nearestCoffeeShopsDataSource.getLocations()

        // TODO: можно перенести в okhttp interceptor
        if (response is Response.Failure.HttpFailure && response.isUnauthorized()) {
            sessionDataSource.setToken(null)
        }
        return response
    }
}
