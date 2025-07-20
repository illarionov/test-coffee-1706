package com.example.coffe1706.data.coffee1706api.retrofit

import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object Coffee1706RetrofitFactory {
    private val applicationJsonMediaType = "application/json; charset=UTF8".toMediaType()

    fun createRetrofit(
        callFactoryProvider: () -> Call.Factory,
        json: Json = Json,
        baseUrl: String = "https://localhost:8080",
    ): Retrofit {
        return Retrofit.Builder().apply {
            callFactory(
                object : Call.Factory {
                    override fun newCall(request: Request): Call = callFactoryProvider().newCall(request)
                },
            )
            baseUrl(baseUrl)
            addConverterFactory(json.asConverterFactory(applicationJsonMediaType))
        }.build()
    }
}
