package com.example.coffe1706.core.model.auth

public class AuthToken(
    public val token: AuthTokenId,
    public val tokenLifeTime: Long,
)

@JvmInline
public value class AuthTokenId(
    public val value: String
)
