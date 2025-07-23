package com.example.coffe1706.feature.coffeeshop.data

import com.example.coffe1706.core.model.LocationId
import com.example.coffe1706.core.model.MenuItem
import com.example.coffe1706.core.model.response.Response
import com.example.coffe1706.core.model.response.isUnauthorized
import com.example.coffe1706.data.coffee1706api.datasource.Coffee1706NetworkDataSource
import com.example.coffe1706.feature.auth.data.AuthRepository
import javax.inject.Inject

interface CoffeeShopMenuRepository {
    suspend fun getMenu(locationId: LocationId): Response<List<MenuItem>>
}

class CoffeeShopMenuRepositoryImpl @Inject constructor(
    private val coffee1706DataSource: Coffee1706NetworkDataSource,
    private val authRepository: AuthRepository,
) :  CoffeeShopMenuRepository {
    override suspend fun getMenu(locationId: LocationId): Response<List<MenuItem>> {
        val response = coffee1706DataSource.getLocationMenu(locationId)
        // TODO: move out
        if (response is Response.Failure.HttpFailure && response.isUnauthorized()) {
            authRepository.logout()
        }
        return response
    }
}
