package com.example.coffe1706.data.coffee1706api.retrofit.okhttp

import com.example.coffe1706.core.model.auth.AuthTokenId
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthHeaderInterceptor(
    val tokenProvider: () -> AuthTokenId?,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider()?.value
        val request = if (token != null) {
            chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }
        return chain.proceed(request)
    }
}
