package com.example.coffe1706

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import com.example.coffe1706.wiring.NetworkModule.RootOkhttpClient
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Provider

@HiltAndroidApp
class Coffee1706Application : SingletonImageLoader.Factory, Application() {

    @set:Inject
    var coilImageLoaderFactory: Provider<CoilImageLoaderFactory>? = null

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return checkNotNull(coilImageLoaderFactory) { "coilImageLoaderFactory is not injected" }
            .get()
            .newImageLoader(context)
    }

    class CoilImageLoaderFactory @Inject constructor(
        @param:RootOkhttpClient val rootOkhttpClient: Provider<OkHttpClient>
    ) : SingletonImageLoader.Factory {
        override fun newImageLoader(context: PlatformContext): ImageLoader {
            val coilOkhttpClient = rootOkhttpClient.get().newBuilder().build()
            return ImageLoader.Builder(context)
                .components {
                    add(
                        OkHttpNetworkFetcherFactory(callFactory = coilOkhttpClient)
                    )
                }
                .memoryCache {
                    MemoryCache.Builder()
                        .maxSizePercent(context, MAX_MEMORY_CACHE_SIZE_PERCENT)
                        .build()
                }
                .diskCache {
                    DiskCache.Builder()
                        .directory(context.cacheDir.resolve("image_cache"))
                        .maxSizeBytes(MAX_DISK_CACHE_SIZE_BYTES)
                        .build()
                }
                .build()
        }

        companion object {
            const val MAX_MEMORY_CACHE_SIZE_PERCENT = 0.10
            const val MAX_DISK_CACHE_SIZE_BYTES = 10_000_000L
        }
    }
}
