package com.example.coffe1706.core.model.response

public sealed class Response<out T: Any> {
    public data class Success<out T: Any>(val value: T) : Response<T>()

    public sealed class Failure : Response<Nothing>() {
        /**
         * Any network error, no HTTP response received
         */
        public class NetworkFailure(
            public val error: Throwable,
        ) : Failure()

        /**
         * 4xx - 5xx HTTP errors
         */
        public class HttpFailure(
            public val httpCode: Int,
            public val httpMessage: String,
            public val rawResponseHeaders: List<Pair<String, String>>?,
            public val rawResponseBody: ByteArray?,
        ) : Failure()

        /**
         * Other HTTP errors
         */
        public class UnknownHttpCodeFailure(
            public val httpCode: Int,
            public val httpMessage: String,
            public val rawResponseHeaders: List<Pair<String, String>>?,
            public val rawResponseBody: ByteArray?,
        ) : Failure()

        /**
         * 2xx HTTP response with unparsable body
         */
        public class ApiFailure(
            public val error: Throwable?,
        ) : Failure()

        /**
         * Other failures
         */
        public class UnknownFailure(
            public val error: Throwable?,
        ) : Failure()
    }
}

suspend inline fun <T : Any, R: Any> Response<T>.map(crossinline block: suspend (T) -> R): Response<R> = when (this) {
    is Response.Success<T> -> Response.Success(block(this.value))
    is Response.Failure -> this
}
