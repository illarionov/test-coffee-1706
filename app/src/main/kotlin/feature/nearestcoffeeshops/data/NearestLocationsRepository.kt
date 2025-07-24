package com.example.coffe1706.feature.nearestcoffeeshops.data

import com.example.coffe1706.core.model.LatLon
import com.example.coffe1706.core.model.Location
import com.example.coffe1706.core.model.response.Response
import com.example.coffe1706.core.model.response.isUnauthorized
import com.example.coffe1706.data.coffee1706api.datasource.Coffee1706NetworkDataSource
import com.example.coffe1706.feature.auth.data.AuthRepository
import javax.inject.Inject

public interface NearestLocationsRepository {
    suspend fun getNearestLocations(location: LatLon?): Response<List<Location>>
}

public class NearestLocationsRepositoryImpl @Inject constructor (
    private val nearestCoffeShopsDataSource: Coffee1706NetworkDataSource,
    private val authRepository: AuthRepository,
) : NearestLocationsRepository {
    override suspend fun getNearestLocations(location: LatLon?): Response<List<Location>> {
        // XXX сервер не учитывает передаваемое текущее местоположение
        val response: Response<List<Location>> =  nearestCoffeShopsDataSource.getLocations()

        // TODO: move out
        if (response is Response.Failure.HttpFailure && response.isUnauthorized()) {
            authRepository.logout()
        }
        return response
    }
}
