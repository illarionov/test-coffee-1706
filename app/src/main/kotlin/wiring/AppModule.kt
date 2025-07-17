package com.example.coffe1706.wiring

import com.example.coffe1706.core.authmanager.AuthManager
import com.example.coffe1706.data.coffee1706api.datasource.Coffee1706SessionDataSource
import com.example.coffe1706.data.coffee1706api.datasource.Coffee1706SessionDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun providesAuthManager(sessionSource: Coffee1706SessionDataSource): AuthManager = sessionSource

    @Module
    @InstallIn(SingletonComponent::class)
    public interface AppModuleBinds {
        @Binds
        @Singleton
        fun bindsSessionDataSource(impl: Coffee1706SessionDataSourceImpl): Coffee1706SessionDataSource
    }
}
