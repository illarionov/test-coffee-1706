package com.example.coffe1706.data.coffee1706api

import com.example.coffe1706.core.model.Location
import com.example.coffe1706.core.model.LocationId
import com.example.coffe1706.core.model.MenuItem
import com.example.coffe1706.core.model.auth.AuthToken
import com.example.coffe1706.core.model.response.Response
import com.example.coffe1706.data.coffee1706api.network.Coffee1706NetworkDataSourceImpl
import com.example.coffe1706.data.coffee1706api.retrofit.service.Coffee1706Service
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import kotlin.coroutines.CoroutineContext

public interface Coffee1706NetworkDataSource {
    public suspend fun login(login: String, password: String): Response<AuthToken>
    public suspend fun register(login: String, password: String): Response<AuthToken>
    public suspend fun getLocations(): Response<List<Location>>
    public suspend fun getLocationMenu(locationId: LocationId): Response<List<MenuItem>>
}

private val applicationJsonMediaType = "application/json; charset=UTF8".toMediaType()

public fun Coffee1706NetworkDataSource(
    callFactory: Call.Factory,
    baseUrl: String = "https://localhost:8080",
    computationDispatcherContext: CoroutineContext = Dispatchers.Default,
): Coffee1706NetworkDataSource {
    val json = Json
    val retrofit = Retrofit.Builder().apply {
        callFactory(callFactory)
        baseUrl(baseUrl)
        addConverterFactory(json.asConverterFactory(applicationJsonMediaType))
    }.build()

    val service = retrofit.create<Coffee1706Service>()
    return Coffee1706NetworkDataSourceImpl(service, computationDispatcherContext)
}
