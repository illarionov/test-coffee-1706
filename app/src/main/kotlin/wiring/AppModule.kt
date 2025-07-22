package com.example.coffe1706.wiring

import com.example.coffe1706.core.authmanager.AuthManager
import com.example.coffe1706.data.coffee1706api.datasource.Coffee1706SessionDataSource
import com.example.coffe1706.data.coffee1706api.datasource.Coffee1706SessionDataSourceImpl
import com.example.coffe1706.data.location.CurrentLocationDataSource
import com.example.coffe1706.data.location.FusedCurrentLocationDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun providesAuthManager(sessionSource: Coffee1706SessionDataSource): AuthManager = sessionSource

    @Module
    @InstallIn(SingletonComponent::class)
    public interface AppModuleBinds {
        @Binds
        @Reusable
        fun bindsSessionDataSource(impl: Coffee1706SessionDataSourceImpl): Coffee1706SessionDataSource

        @Binds
        @Reusable
        fun bindsCurrentLocationDataSource(impl: FusedCurrentLocationDataSource): CurrentLocationDataSource
    }
}
