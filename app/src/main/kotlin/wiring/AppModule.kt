package com.example.coffe1706.wiring

import android.content.Context
import com.example.coffe1706.core.ui.component.snackbar.SnackbarController
import com.example.coffe1706.data.location.CurrentLocationDataSource
import com.example.coffe1706.data.location.FusedCurrentLocationDataSource
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesSnackbarController(): SnackbarController = SnackbarController()

    @Provides
    @Reusable
    fun providesCurrentLocationDataSource(
        @ApplicationContext context: Context,
    ): CurrentLocationDataSource = FusedCurrentLocationDataSource(context)
}
