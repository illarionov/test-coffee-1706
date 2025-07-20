package com.example.coffe1706.wiring

import android.content.Context
import com.example.coffe1706.BuildConfig
import com.example.coffe1706.data.coffee1706api.datasource.Coffee1706SessionDataSource
import com.example.coffe1706.data.coffee1706api.retrofit.Coffee1706RetrofitFactory
import com.example.coffe1706.data.coffee1706api.retrofit.okhttp.AuthHeaderInterceptor
import com.example.coffe1706.data.coffee1706api.retrofit.service.Coffee1706Service
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Module
@InstallIn(SingletonComponent::class)
public object NetworkModule {
    private const val OKHTTP_CACHE_SUBDIR = "coffee1706"

    @Provides
    @Reusable
    internal fun providesCoffee1706Service(
        @Coffee1706Client retrofit: Retrofit,
    ): Coffee1706Service = retrofit.create<Coffee1706Service>()

    @Provides
    @Singleton
    @Coffee1706Client
    fun providesCoffee1706Retrofit(
        @Coffee1706Client okhttpClient: dagger.Lazy<@JvmSuppressWildcards OkHttpClient>,
        json: Json,
        @Coffee1706Client baseUrl: String,
    ): Retrofit {
        return Coffee1706RetrofitFactory.createRetrofit(okhttpClient::get, json, baseUrl)
    }

    @Provides
    @Singleton
    @Coffee1706Client
    fun provideCoffee1706OkhttpClient(
        @RootOkhttpClient rootOkhttpClient: dagger.Lazy<@JvmSuppressWildcards OkHttpClient>,
        cache: dagger.Lazy<@JvmSuppressWildcards Cache>,
        sessionDataSource: Coffee1706SessionDataSource,
    ): OkHttpClient {
        val authHeaderInterceptor = AuthHeaderInterceptor(
            tokenProvider = sessionDataSource::getTokenBlocking
        )

        return rootOkhttpClient.get().newBuilder()
            .cache(cache.get())
            .addInterceptor(authHeaderInterceptor)
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

    @Provides
    @Singleton
    @RootOkhttpClient
    fun providesRootOkhttpClient(
        @LoggingInterceptor loggingInterceptor: Interceptor?,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (loggingInterceptor != null) {
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
                addInterceptor(loggingInterceptor)
            }
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

    @Provides
    @Reusable
    fun providesJson(): Json = Json

    @Qualifier
    annotation class RootOkhttpClient

    @Qualifier
    annotation class Coffee1706Client

    @Qualifier
    annotation class LoggingInterceptor
}
