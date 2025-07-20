package com.example.coffe1706.data.coffee1706api.datasource

import com.example.coffe1706.core.model.Location
import com.example.coffe1706.core.model.LocationId
import com.example.coffe1706.core.model.MenuItem
import com.example.coffe1706.core.model.auth.AuthToken
import com.example.coffe1706.core.model.response.Response
import com.example.coffe1706.core.model.response.map
import com.example.coffe1706.data.coffee1706api.retrofit.runRetrofitRequest
import com.example.coffe1706.data.coffee1706api.retrofit.service.Coffee1706Service
import com.example.coffe1706.data.coffee1706api.retrofit.service.LocationDto
import com.example.coffe1706.data.coffee1706api.retrofit.service.LocationMenuItemDto
import com.example.coffe1706.data.coffee1706api.retrofit.service.LoginRequestDto
import com.example.coffe1706.data.coffee1706api.retrofit.service.LoginResponseDto
import com.example.coffe1706.wiring.CoroutinesModule.ComputationDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class Coffee1706NetworkDataSource @Inject internal constructor(
    private val service: Coffee1706Service,
    @param:ComputationDispatcher private val computationDispatcherContext: CoroutineContext,
) {
    suspend fun login(
        login: String,
        password: String,
    ): Response<AuthToken> {
        return runRetrofitRequest {
            service.login(LoginRequestDto(login, password))
        }.map(LoginResponseDto::toAuthToken)
    }

    suspend fun register(
        login: String,
        password: String,
    ): Response<AuthToken> {
        return runRetrofitRequest {
            service.register(LoginRequestDto(login, password))
        }.map(LoginResponseDto::toAuthToken)
    }

    suspend fun getLocations(): Response<List<Location>> {
        return runRetrofitRequest {
            service.getLocations()
        }.map { list ->
            withContext(computationDispatcherContext) {
                list.map(LocationDto::toLocation)
            }
        }
    }

    suspend fun getLocationMenu(locationId: LocationId): Response<List<MenuItem>> {
        return runRetrofitRequest {
            service.getMenu(locationId.id)
        }.map { list ->
            withContext(computationDispatcherContext) {
                list.map(LocationMenuItemDto::toLocationMenuItem)
            }
        }
    }
}
