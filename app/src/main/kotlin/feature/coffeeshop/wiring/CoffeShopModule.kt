package com.example.coffe1706.feature.coffeeshop.wiring

import com.example.coffe1706.feature.coffeeshop.data.CoffeeShopMenuRepository
import com.example.coffe1706.feature.coffeeshop.data.CoffeeShopMenuRepositoryImpl
import com.example.coffe1706.feature.coffeeshop.data.InMemoryShoppingCartRepository
import com.example.coffe1706.feature.coffeeshop.data.ShoppingCartRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

object CoffeShopModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface CoffeShopAppModule {
        @Binds
        @Singleton
        fun bindsShoppingCartRepository(impl: InMemoryShoppingCartRepository): ShoppingCartRepository

        @Binds
        @Reusable
        fun bindsMenuRepository(impl: CoffeeShopMenuRepositoryImpl): CoffeeShopMenuRepository
    }
}
