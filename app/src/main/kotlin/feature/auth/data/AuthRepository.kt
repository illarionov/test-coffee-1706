package com.example.coffe1706.feature.auth.data

import com.example.coffe1706.core.model.auth.AuthToken
import com.example.coffe1706.core.model.response.Response
import com.example.coffe1706.data.coffee1706api.datasource.Coffee1706NetworkDataSource
import com.example.coffe1706.data.coffee1706api.datasource.Coffee1706SessionDataSource
import kotlinx.coroutines.ensureActive
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

interface AuthRepository {
    suspend fun login(
        login: String,
        password: String,
    ): Response<Unit>

    suspend fun register(
        login: String,
        password: String,
    ): Response<Unit>

    suspend fun logout(): Response<Unit>
}

class AuthRepositoryImpl @Inject internal constructor (
    private val networkSource: Coffee1706NetworkDataSource,
    private val sessionLocalDataSource: Coffee1706SessionDataSource,
) : AuthRepository {
    override suspend fun login(
        login: String,
        password: String,
    ): Response<Unit> {
        val tokenResult = networkSource.login(login, password)
        val token: AuthToken = when (tokenResult) {
            is Response.Failure -> return tokenResult
            is Response.Success -> tokenResult.value
        }
        try {
            sessionLocalDataSource.setToken(token.token)
        } catch (ex: RuntimeException) {
            coroutineContext.ensureActive()
            return Response.Failure.UnknownFailure(ex)
        }
        return Response.Success(Unit)
    }

    override suspend fun register(
        login: String,
        password: String,
    ): Response<Unit> {
        val tokenResult = networkSource.register(login, password)
        val token: AuthToken = when (tokenResult) {
            is Response.Failure -> return tokenResult
            is Response.Success -> tokenResult.value
        }
        try {
            sessionLocalDataSource.setToken(token.token)
        } catch (ex: RuntimeException) {
            coroutineContext.ensureActive()
            return Response.Failure.UnknownFailure(ex)
        }
        return Response.Success(Unit)
    }

    override suspend fun logout(): Response<Unit> {
        try {
            sessionLocalDataSource.setToken(null)
        } catch (ex: RuntimeException) {
            coroutineContext.ensureActive()
            return Response.Failure.UnknownFailure(ex)
        }
        return Response.Success(Unit)
    }
}
