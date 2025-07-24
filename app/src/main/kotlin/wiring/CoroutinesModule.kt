package com.example.coffe1706.wiring

import com.example.coffe1706.core.di.AppMainCoroutineScope
import com.example.coffe1706.core.di.ComputationDispatcher
import com.example.coffe1706.core.di.IoCoroutineDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
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
    fun providesComputationDispatcherContext(): CoroutineContext = Dispatchers.Default
}
