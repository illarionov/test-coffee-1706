package com.example.coffe1706.feature.coffeeshop.data

import com.example.coffe1706.core.model.LocationId
import com.example.coffe1706.core.model.MenuItem
import com.example.coffe1706.core.model.response.Response
import com.example.coffe1706.core.model.response.isUnauthorized
import com.example.coffe1706.data.coffee1706api.Coffee1706NetworkDataSource
import com.example.coffe1706.data.coffee1706api.session.Coffee1706SessionDataSource
import javax.inject.Inject

internal interface CoffeeShopMenuRepository {
    suspend fun getMenu(locationId: LocationId): Response<List<MenuItem>>
}

internal class CoffeeShopMenuRepositoryImpl @Inject constructor(
    private val coffee1706DataSource: Coffee1706NetworkDataSource,
    private val sessionDataSource: Coffee1706SessionDataSource,
) :  CoffeeShopMenuRepository {
    override suspend fun getMenu(locationId: LocationId): Response<List<MenuItem>> {
        val response = coffee1706DataSource.getLocationMenu(locationId)
        // TODO: можно перенести в okhttp interceptor
        if (response is Response.Failure.HttpFailure && response.isUnauthorized()) {
            sessionDataSource.setToken(null)
        }
        return response
    }
}
