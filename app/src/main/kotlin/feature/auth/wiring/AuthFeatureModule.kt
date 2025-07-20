package com.example.coffe1706.feature.auth.wiring

import com.example.coffe1706.feature.auth.data.AuthRepository
import com.example.coffe1706.feature.auth.data.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface AuthFeatureModule {

    @Binds
    @ViewModelScoped
    fun bindsAuthRepository(impl: AuthRepositoryImpl): AuthRepository

}
