package com.example.coffe1706.feature.nearestcoffeeshops.wiring

import com.example.coffe1706.feature.nearestcoffeeshops.data.NearestLocationsRepository
import com.example.coffe1706.feature.nearestcoffeeshops.data.NearestLocationsRepositoryImpl
import com.example.coffe1706.feature.nearestcoffeeshops.domain.GetNearestLocationsUseCase
import com.example.coffe1706.feature.nearestcoffeeshops.domain.GetNearestLocationsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface NearestCoffeShopsModule {
    @Binds
    @Reusable
    fun bindsNearestLocationsRepository(impl: NearestLocationsRepositoryImpl): NearestLocationsRepository

    @Binds
    @Reusable
    fun bindsGetNearestLocationsUseCase(impl: GetNearestLocationsUseCaseImpl): GetNearestLocationsUseCase
}
