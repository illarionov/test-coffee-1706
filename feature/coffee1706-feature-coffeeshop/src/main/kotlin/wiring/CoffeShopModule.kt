package com.example.coffe1706.feature.coffeeshop.wiring

import com.example.coffe1706.feature.coffeeshop.data.CoffeeShopMenuRepository
import com.example.coffe1706.feature.coffeeshop.data.CoffeeShopMenuRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface CoffeShopModule {
    @Binds
    @Reusable
    fun bindsMenuRepository(impl: CoffeeShopMenuRepositoryImpl): CoffeeShopMenuRepository
}
