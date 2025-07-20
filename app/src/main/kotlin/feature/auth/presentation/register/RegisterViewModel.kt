package com.example.coffe1706.feature.auth.presentation.register

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffe1706.R
import com.example.coffe1706.core.model.response.Response
import com.example.coffe1706.core.ui.component.snackbar.SnackbarController
import com.example.coffe1706.core.ui.internationalization.message.LocalizedMessage
import com.example.coffe1706.feature.auth.data.AuthRepository
import com.example.coffe1706.feature.auth.data.getLoginErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val snackbarController: SnackbarController,
) : ViewModel() {
    private val _labels: MutableSharedFlow<Label> = MutableSharedFlow()
    val labels: SharedFlow<Label> = _labels

    val emailTextFieldState: TextFieldState = TextFieldState()
    val passwordTextFieldState: TextFieldState = TextFieldState()
    val reenterPasswordTextFieldState: TextFieldState = TextFieldState()
    var isLoading: Boolean by mutableStateOf(false)
        private set
    var showFieldErrors: Boolean by mutableStateOf(false)
        private set

    val reenterPasswordError: LocalizedMessage? by derivedStateOf {
        if (showFieldErrors) {
            validatePasswordField(
                passwordTextFieldState.text.toString(),
                reenterPasswordTextFieldState.text.toString(),
            )?.error
        } else {
            null
        }
    }

    fun register() {
        if (isLoading) return

        val email = emailTextFieldState.text.toString()
        val password = passwordTextFieldState.text.toString()
        val retryPassword = reenterPasswordTextFieldState.text.toString()
        val validationError = validatePasswordField(password, retryPassword)
        if (validationError != null) {
            showFieldErrors = true
            return
        }

        viewModelScope.launch {
            if (isLoading) return@launch
            isLoading = true

            val result = authRepository.register(email, password)
            when (result) {
                is Response.Success<*> -> {
                    _labels.emit(Label.RegisterSuccess)
                }

                is Response.Failure -> {
                    snackbarController.enqueueMessage(result.getLoginErrorMessage())
                    isLoading = false
                }
            }
        }
    }

    public sealed interface Label {
        data object RegisterSuccess : Label
    }

    companion object {
        fun validatePasswordField(
            password: String,
            retryPassword: String,
        ): FormValidationError? {
            return if (password != retryPassword) {
                FormValidationError(
                    FormField.RETRY_PASSWORD,
                    LocalizedMessage(R.string.password_do_not_match),
                )
            } else {
                null
            }
        }

        data class FormValidationError(
            val field: FormField,
            val error: LocalizedMessage,
        )

        enum class FormField { EMAIL, PASSWORD, RETRY_PASSWORD, }
    }
}
