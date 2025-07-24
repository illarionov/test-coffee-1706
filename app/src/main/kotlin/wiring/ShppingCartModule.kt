package com.example.coffe1706.wiring

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.coffe1706.data.shoppingcart.DatastoreShoppingCartRepository
import com.example.coffe1706.data.shoppingcart.ShoppingCartRepository
import com.example.coffe1706.data.shoppingcart.dto.ShoppingCartsDto
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.shoppingCartsDataStore: DataStore<ShoppingCartsDto> by dataStore(
    fileName = "carts.pb",
    serializer = DatastoreShoppingCartRepository.ShoppingCartsDtoSerializer
)

@Module
@InstallIn(SingletonComponent::class)
object ShoppingCartsModule {
    @Provides
    @Reusable
    fun providesShoppingCartsDataStore(
        @ApplicationContext context: Context
    ): DataStore<ShoppingCartsDto> = context.shoppingCartsDataStore

    @Module
    @InstallIn(SingletonComponent::class)
    interface ShoppingCartsModuleBinds {
        @Binds
        @Singleton
        //fun bindsShoppingCartRepository(impl: InMemoryShoppingCartRepository): ShoppingCartRepository
        fun bindsShoppingCartRepository(impl: DatastoreShoppingCartRepository): ShoppingCartRepository
    }
}
