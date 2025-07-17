package com.example.coffe1706.data.coffee1706api.retrofit

import com.example.coffe1706.core.model.response.Response

internal fun <T: Any> retrofit2.Response<T>.toResponse(): Response<T> {
    val body = this.body()
    if (this.isSuccessful) {
        return if (body != null) {
            Response.Success(body)
        } else {
            Response.Failure.ApiFailure(NullPointerException("Body is null"))
        }
    }

    return when (this.code()) {
        in 200 until 300 -> Response.Failure.ApiFailure(IllegalStateException("Http code 200 with error"))
        in 400 until 600 -> Response.Failure.HttpFailure(
            httpCode = this.code(),
            httpMessage = this.message(),
            rawResponseHeaders = this.headers().toList(),
            rawResponseBody = this.errorBody()?.bytes(),
        )
        else -> Response.Failure.UnknownHttpCodeFailure(
            httpCode = this.code(),
            httpMessage = this.message(),
            rawResponseHeaders = this.headers().toList(),
            rawResponseBody = this.errorBody()?.bytes(),
        )
    }
}
