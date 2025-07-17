package com.example.coffe1706.data.coffee1706api.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.coffe1706.core.authmanager.AuthManager
import com.example.coffe1706.core.model.auth.AuthTokenId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

private val COFFEE_1706_AUTH_TOKEN_KEY = stringPreferencesKey("coffee1706-token")

interface Coffee1706SessionDataSource : AuthManager {
    val tokenFlow: Flow<AuthTokenId?>
    suspend fun setToken(token: AuthTokenId?)

    fun getTokenBlocking(): AuthTokenId?

    override val isUserLoggedIn: Flow<Boolean> get() = tokenFlow.map { it != null }
}

@Singleton
class Coffee1706SessionDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : Coffee1706SessionDataSource {
    override val tokenFlow: Flow<AuthTokenId?> = dataStore.data.map(::getTokenFromPreferences)

    override suspend fun setToken(token: AuthTokenId?) {
        dataStore.edit { settings: MutablePreferences ->
            if (token != null) {
                settings[COFFEE_1706_AUTH_TOKEN_KEY] = token.value
            } else {
                settings.remove(COFFEE_1706_AUTH_TOKEN_KEY)
            }
        }
    }

    override fun getTokenBlocking(): AuthTokenId? {
        return runBlocking { tokenFlow.first() }
    }

    private companion object {
        fun getTokenFromPreferences(preferences: Preferences): AuthTokenId? {
            val tokenString: String? = preferences[COFFEE_1706_AUTH_TOKEN_KEY]
            return tokenString?.let(::AuthTokenId)
        }
    }
}
