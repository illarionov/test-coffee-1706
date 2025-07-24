package com.example.coffe1706.feature.auth.presentation

import com.example.coffe1706.core.model.response.Response
import com.example.coffe1706.core.ui.R
import com.example.coffe1706.core.ui.internationalization.getCommonErrorMessage
import com.example.coffe1706.core.ui.internationalization.message.LocalizedMessage

internal fun Response.Failure.HttpFailure.isLoginUserNotFound() = this.httpCode == 404

internal fun Response.Failure.HttpFailure.isLoginOrPasswordError() = this.httpCode == 400

internal fun Response.Failure.getLoginErrorMessage(): LocalizedMessage = when (this) {
    is Response.Failure.HttpFailure -> when  {
        isLoginUserNotFound() -> LocalizedMessage(R.string.response_error_user_not_found)
        isLoginOrPasswordError() -> LocalizedMessage(R.string.response_error_user_input_error)
        else -> getCommonErrorMessage()
    }
    else -> getCommonErrorMessage()
}
