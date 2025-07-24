package com.example.coffe1706.data.coffee1706api.retrofit.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface Coffee1706Service {
    @POST("/auth/login")
    suspend fun login(@Body loginForm: LoginRequestDto): Response<LoginResponseDto>

    @POST("/auth/register")
    suspend fun register(@Body loginForm: LoginRequestDto): Response<LoginResponseDto>

    @GET("/locations")
    suspend fun getLocations(): Response<List<LocationDto>>

    @GET("/location/{id}/menu")
    suspend fun getMenu(@Path("id") id: String): Response<List<LocationMenuItemDto>>
}
