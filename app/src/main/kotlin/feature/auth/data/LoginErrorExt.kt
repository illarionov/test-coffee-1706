package com.example.coffe1706.feature.auth.data

import com.example.coffe1706.R
import com.example.coffe1706.core.model.response.Response
import com.example.coffe1706.core.ui.internationalization.getCommonErrorMessage
import com.example.coffe1706.core.ui.internationalization.message.LocalizedMessage


public fun Response.Failure.HttpFailure.isLoginUserNotFound() = this.httpCode == 404

public fun Response.Failure.HttpFailure.isLoginOrPasswordError() = this.httpCode == 400

public fun Response.Failure.getLoginErrorMessage(): LocalizedMessage = when (this) {
    is Response.Failure.HttpFailure -> when  {
        isLoginUserNotFound() -> LocalizedMessage(R.string.response_error_user_not_found)
        isLoginOrPasswordError() -> LocalizedMessage(R.string.response_error_user_input_error)
        else -> getCommonErrorMessage()
    }
    else -> getCommonErrorMessage()
}
