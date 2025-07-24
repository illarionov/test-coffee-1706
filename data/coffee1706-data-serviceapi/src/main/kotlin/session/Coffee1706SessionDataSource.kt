package com.example.coffe1706.data.coffee1706api.session

import com.example.coffe1706.core.model.auth.AuthTokenId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * DataSource, хранящий и предоставляющий токен для сетевых запросов к Coffee API
 */
public interface Coffee1706SessionDataSource : Coffee1706AuthManager {
    public val tokenFlow: Flow<AuthTokenId?>
    public suspend fun setToken(token: AuthTokenId?)

    public fun getTokenBlocking(): AuthTokenId?

    override val isUserLoggedIn: Flow<Boolean> get() = tokenFlow.map { it != null }
}
