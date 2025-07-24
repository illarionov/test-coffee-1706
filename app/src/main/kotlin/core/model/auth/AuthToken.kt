package com.example.coffe1706.core.model.auth

public class AuthToken(
    val token: AuthTokenId,
    val tokenLifeTime: Long,
)

@JvmInline
public value class AuthTokenId(
    val value: String
)
