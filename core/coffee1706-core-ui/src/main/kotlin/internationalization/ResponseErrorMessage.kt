package com.example.coffe1706.core.ui.internationalization

import com.example.coffe1706.core.model.response.Response
import com.example.coffe1706.core.ui.R
import com.example.coffe1706.core.ui.internationalization.message.LocalizedMessage

public fun Response.Failure.getCommonErrorMessage(): LocalizedMessage = when (this) {
    is Response.Failure.ApiFailure -> LocalizedMessage(R.string.response_error_api_failure)
    is Response.Failure.HttpFailure -> when (this.httpCode) {
        400 -> LocalizedMessage(R.string.response_error_user_input_error)
        401 -> LocalizedMessage(R.string.response_error_user_unathorized)
        404 -> LocalizedMessage(R.string.response_error_user_not_found)
        in 400..499 -> LocalizedMessage(
            R.string.response_error_authorization_error,
            listOf(this.httpCode, this.httpMessage),
        )

        in 500..599 -> LocalizedMessage(R.string.response_error_server_temporarily_error)
        else -> LocalizedMessage(R.string.response_error_unknown_http_code_failure, listOf(this.httpCode))
    }

    is Response.Failure.NetworkFailure -> when {
        this.error is java.net.UnknownHostException
                || this.error is java.net.SocketException -> LocalizedMessage(R.string.response_error_no_internet_connection)
        else -> LocalizedMessage(R.string.response_error_network_failure)
    }

    is Response.Failure.UnknownFailure -> LocalizedMessage(R.string.response_error_unknown_failure)
    is Response.Failure.UnknownHttpCodeFailure -> LocalizedMessage(
        R.string.response_error_unknown_http_code_failure,
        listOf(this.httpCode),
    )
}
