package com.example.coffe1706.feature.auth.presentation.login

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffe1706.core.model.response.Response
import com.example.coffe1706.core.ui.component.snackbar.SnackbarController
import com.example.coffe1706.feature.auth.data.AuthRepository
import com.example.coffe1706.feature.auth.data.getLoginErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val snackbarController: SnackbarController,
) : ViewModel() {
    private val _labels: MutableSharedFlow<Label> = MutableSharedFlow()
    val labels: SharedFlow<Label> = _labels

    val emailTextFieldState: TextFieldState = TextFieldState()
    val passwordTextFieldState: TextFieldState = TextFieldState()
    var isLoading: Boolean by mutableStateOf(false)
        private set


    fun login() {
        val email = emailTextFieldState.text.toString()
        val password = passwordTextFieldState.text.toString()
        viewModelScope.launch {
            if (isLoading) return@launch
            isLoading = true

            val result = authRepository.login(email, password)
            when (result) {
                is Response.Failure -> {
                    snackbarController.enqueueMessage(result.getLoginErrorMessage())
                    isLoading = false
                }
                is Response.Success<*> -> {
                    _labels.emit(Label.LoginSuccess)
                }
            }
        }
    }

    public sealed interface Label {
        data object LoginSuccess: Label
    }
}
