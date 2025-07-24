package com.example.coffe1706.wiring

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.coffe1706.data.shoppingcart.DatastoreShoppingCartRepository
import com.example.coffe1706.data.shoppingcart.ShoppingCartRepository
import com.example.coffe1706.data.shoppingcart.dto.ShoppingCartsDto
import dagger.Module
import dagger.Provides
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
    @Singleton
    fun providesShoppingCartRepository(
        @ApplicationContext context: Context
    ): ShoppingCartRepository = DatastoreShoppingCartRepository(context.shoppingCartsDataStore)
}
