package com.example.coffe1706.data.coffee1706api.retrofit

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object Coffee1706RetrofitFactory {
    private val applicationJsonMediaType = "application/json; charset=UTF8".toMediaType()

    fun createRetrofit(
        callFactory: okhttp3.Call.Factory,
        json: Json = Json,
        baseUrl: String = "https://localhost:8080"
    ) : Retrofit {
        return Retrofit.Builder().apply {
            callFactory(callFactory)
            baseUrl(baseUrl)
            addConverterFactory(json.asConverterFactory(applicationJsonMediaType))
        }.build()
    }
}
