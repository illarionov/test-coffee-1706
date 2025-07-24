package com.example.coffe1706.data.coffee1706api.network

import com.example.coffe1706.core.model.Location
import com.example.coffe1706.core.model.LocationId
import com.example.coffe1706.core.model.MenuItem
import com.example.coffe1706.core.model.auth.AuthToken
import com.example.coffe1706.core.model.response.Response
import com.example.coffe1706.core.model.response.map
import com.example.coffe1706.data.coffee1706api.Coffee1706NetworkDataSource
import com.example.coffe1706.data.coffee1706api.datasource.toAuthToken
import com.example.coffe1706.data.coffee1706api.datasource.toLocation
import com.example.coffe1706.data.coffee1706api.datasource.toLocationMenuItem
import com.example.coffe1706.data.coffee1706api.retrofit.runRetrofitRequest
import com.example.coffe1706.data.coffee1706api.retrofit.service.Coffee1706Service
import com.example.coffe1706.data.coffee1706api.retrofit.service.LocationDto
import com.example.coffe1706.data.coffee1706api.retrofit.service.LocationMenuItemDto
import com.example.coffe1706.data.coffee1706api.retrofit.service.LoginRequestDto
import com.example.coffe1706.data.coffee1706api.retrofit.service.LoginResponseDto
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class Coffee1706NetworkDataSourceImpl(
    private val service: Coffee1706Service,
    private val computationDispatcherContext: CoroutineContext,
) : Coffee1706NetworkDataSource {
    override suspend fun login(
        login: String,
        password: String,
    ): Response<AuthToken> {
        return runRetrofitRequest {
            service.login(LoginRequestDto(login, password))
        }.map(LoginResponseDto::toAuthToken)
    }

    override suspend fun register(
        login: String,
        password: String,
    ): Response<AuthToken> {
        return runRetrofitRequest {
            service.register(LoginRequestDto(login, password))
        }.map(LoginResponseDto::toAuthToken)
    }

    override suspend fun getLocations(): Response<List<Location>> {
        return runRetrofitRequest {
            service.getLocations()
        }.map { list ->
            withContext(computationDispatcherContext) {
                list.map(LocationDto::toLocation)
            }
        }
    }

    override suspend fun getLocationMenu(locationId: LocationId): Response<List<MenuItem>> {
        return runRetrofitRequest {
            service.getMenu(locationId.id)
        }.map { list ->
            withContext(computationDispatcherContext) {
                list.map(LocationMenuItemDto::toLocationMenuItem)
            }
        }
    }
}
