package com.example.coffe1706.feature.coffeeshop.wiring

import com.example.coffe1706.feature.coffeeshop.data.CoffeeShopMenuRepository
import com.example.coffe1706.feature.coffeeshop.data.CoffeeShopMenuRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

object CoffeShopModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface CoffeShopAppModule {
        @Binds
        @Reusable
        fun bindsMenuRepository(impl: CoffeeShopMenuRepositoryImpl): CoffeeShopMenuRepository
    }
}
