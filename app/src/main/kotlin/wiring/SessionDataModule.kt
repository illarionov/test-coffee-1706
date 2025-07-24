package com.example.coffe1706.wiring

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.coffe1706.data.coffee1706api.datasource.dto.AuthTokenDto
import com.example.coffe1706.data.coffee1706api.session.Coffee1706AuthManager
import com.example.coffe1706.data.coffee1706api.session.Coffee1706SessionDataSource
import com.example.coffe1706.data.coffee1706api.session.DataStoreCoffee1706SessionDataSource
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

private val Context.authtokenDataStore: DataStore<AuthTokenDto> by dataStore(
    fileName = "authtoken.pb",
    serializer = DataStoreCoffee1706SessionDataSource.AuthTokenDtoSerializer
)

@Module
@InstallIn(SingletonComponent::class)
object SessionDataModule {
    @Provides
    fun providesAuthManager(sessionSource: Coffee1706SessionDataSource): Coffee1706AuthManager = sessionSource

    @Provides
    @Reusable
    fun providesSessionDataSource(
        @ApplicationContext context: Context
    ): Coffee1706SessionDataSource {
        val dataStore = context.authtokenDataStore
        return DataStoreCoffee1706SessionDataSource(dataStore)
    }
}
