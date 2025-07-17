package com.example.coffe1706.wiring

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {
    @Provides
    @AppMainCoroutineScope
    @Singleton
    fun providesAppMainCoroutineScope(): CoroutineScope = MainScope()

    @Provides
    @IoCoroutineDispatcher
    fun providesIoCoroutineDispatcherContext(): CoroutineContext = Dispatchers.IO

    @Provides
    @ComputationDispatcher
    fun providesComputationDispathcerContext(): CoroutineContext = Dispatchers.Default

    @Qualifier annotation class AppMainCoroutineScope
    @Qualifier annotation class IoCoroutineDispatcher
    @Qualifier annotation class ComputationDispatcher
}
