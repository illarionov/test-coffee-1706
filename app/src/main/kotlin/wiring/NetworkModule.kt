package com.example.coffe1706.wiring

import android.content.Context
import com.example.coffe1706.BuildConfig
import com.example.coffe1706.core.di.ComputationDispatcher
import com.example.coffe1706.data.coffee1706api.Coffee1706NetworkDataSource
import com.example.coffe1706.data.coffee1706api.session.Coffee1706SessionDataSource
import com.example.coffe1706.data.coffee1706api.util.okhttp.Coffee1706AuthHeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Call
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Module
@InstallIn(SingletonComponent::class)
public object NetworkModule {
    private const val OKHTTP_CACHE_SUBDIR = "coffee1706"

    @Provides
    @Singleton
    internal fun providesCoffee1706NetworkDataSource(
        @Coffee1706Client okhttpClient: dagger.Lazy<@JvmSuppressWildcards OkHttpClient>,
        @ComputationDispatcher computationDispatcher: CoroutineContext,
        @Coffee1706Client baseUrl: String,
    ): Coffee1706NetworkDataSource {
        val lazyCallFactory = Call.Factory { request: Request ->
            okhttpClient.get().newCall(request)
        }

        return Coffee1706NetworkDataSource(
            callFactory = lazyCallFactory,
            baseUrl = baseUrl,
            computationDispatcherContext = computationDispatcher,
        )
    }

    /**
     * Okhhtp клиент для Coffee API с auth interceptor
     */
    @Provides
    @Singleton
    @Coffee1706Client
    fun provideCoffee1706OkhttpClient(
        @RootOkhttpClient rootOkhttpClient: dagger.Lazy<@JvmSuppressWildcards OkHttpClient>,
        cache: dagger.Lazy<@JvmSuppressWildcards Cache>,
        sessionDataSource: Coffee1706SessionDataSource,
        @LoggingInterceptor loggingInterceptor: Interceptor?,
    ): OkHttpClient {
        val authHeaderInterceptor = Coffee1706AuthHeaderInterceptor(
            tokenProvider = sessionDataSource::getTokenBlocking
        )

        return rootOkhttpClient.get().newBuilder()
            .cache(cache.get())
            .addInterceptor(authHeaderInterceptor)
            .apply {
                if (loggingInterceptor != null) {
                    addInterceptor(loggingInterceptor)
                }
            }
            .build()
    }

    @Provides
    @Coffee1706Client
    fun providesCoffee1706BaseUrl(): String = BuildConfig.COFFEE_1706_API_URL

    @Provides
    @Singleton
    fun provideCache(
        @ApplicationContext context: Context,
    ): Cache {
        @Suppress("MagicNumber")
        return Cache(
            File(context.cacheDir, OKHTTP_CACHE_SUBDIR),
            maxSize = 10 * 1_000_000L,
        )
    }

    /**
     * Базовый okhttp клиент, от которого создаются клиенты под конкретные сервисы.
     *
     * Используется для Coil и для сервера Coffee.
     */
    @Provides
    @Singleton
    @RootOkhttpClient
    fun providesRootOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(20.seconds.inWholeMilliseconds, TimeUnit.MILLISECONDS)
            readTimeout(20.seconds.inWholeMilliseconds, TimeUnit.MILLISECONDS)
            writeTimeout(20.seconds.inWholeMilliseconds, TimeUnit.MILLISECONDS)
            connectionPool(
                ConnectionPool(
                    maxIdleConnections = 10,
                    keepAliveDuration = 2.minutes.inWholeMilliseconds,
                    timeUnit = TimeUnit.MILLISECONDS,
                ),
            )
            dispatcher(
                Dispatcher().apply {
                    maxRequestsPerHost = 10
                },
            )
        }.build()
    }

    @Provides
    @LoggingInterceptor
    @Reusable
    fun providesLoggingInterceptor(): Interceptor? {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().apply {
                // setLevel(HttpLoggingInterceptor.Level.HEADERS)
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
        } else {
            null
        }
    }

    @Qualifier
    annotation class RootOkhttpClient

    @Qualifier
    annotation class Coffee1706Client

    @Qualifier
    annotation class LoggingInterceptor
}
