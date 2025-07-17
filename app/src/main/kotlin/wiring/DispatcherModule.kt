package com.example.coffe1706.wiring

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Provides
    @IoCoroutineDispatcher
    fun providesIoCoroutineDispatcherContext(): CoroutineContext = Dispatchers.IO

    @Provides
    @ComputationDispatcher
    fun providesComputationDispathcerContext(): CoroutineContext = Dispatchers.Default

    @Qualifier annotation class IoCoroutineDispatcher
    @Qualifier annotation class ComputationDispatcher
}
