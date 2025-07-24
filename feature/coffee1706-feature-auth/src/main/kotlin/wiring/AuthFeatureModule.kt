package com.example.coffe1706.feature.auth.wiring

import com.example.coffe1706.feature.auth.data.AuthRepository
import com.example.coffe1706.feature.auth.data.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface AuthFeatureModule {
    @Binds
    @Reusable
    fun bindsAuthRepository(impls: AuthRepositoryImpl): AuthRepository
}
