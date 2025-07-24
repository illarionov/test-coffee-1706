package com.example.coffe1706.wiring

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.coffe1706.data.coffee1706api.datasource.Coffee1706SessionDataSourceImpl
import com.example.coffe1706.data.coffee1706api.datasource.dto.AuthTokenDto
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

val Context.dataStore: DataStore<AuthTokenDto> by dataStore(
    fileName = "authtoken.pb",
    serializer = Coffee1706SessionDataSourceImpl.AuthTokenDtoSerializer
)

@Module
@InstallIn(SingletonComponent::class)
object AuthtokenDataModule {
    @Provides
    @Reusable
    fun providesDataStore(
        @ApplicationContext context: Context
    ): DataStore<AuthTokenDto> = context.dataStore
}
