package com.example.coffe1706.data.coffee1706api.retrofit.service

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String,
    @SerialName("tokenLifetime") val tokenLifeTime: Long,
)
