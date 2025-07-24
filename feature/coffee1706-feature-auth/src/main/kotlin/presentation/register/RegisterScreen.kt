package com.example.coffe1706.feature.auth.presentation.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coffe1706.core.ui.design.button.PrimaryActionButton
import com.example.coffe1706.core.ui.design.textfield.BaseSecureTextField
import com.example.coffe1706.core.ui.design.textfield.BaseTextField
import com.example.coffe1706.core.ui.internationalization.message.LocalizedMessage
import com.example.coffe1706.core.ui.internationalization.message.stringResource
import com.example.coffe1706.core.ui.theme.Coffee1706Theme
import com.example.coffe1706.feature.auth.R
import com.example.coffe1706.feature.auth.presentation.LoginRegisterUiDefaults

@Composable
internal fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    LaunchedEffect(viewModel.labels) {
        viewModel.labels.collect { label ->
            when (label) {
                RegisterViewModel.Label.RegisterSuccess -> onRegisterSuccess()
            }
        }
    }

    RegisterScreen(
        onNavigateToLogin = onNavigateToLogin,
        onSendRegisterForm = viewModel::register,
        animatedVisibilityScope = animatedVisibilityScope,
        sharedTransitionScope = sharedTransitionScope,
        emailTextFieldState = viewModel.emailTextFieldState,
        passwordTextFieldState = viewModel.passwordTextFieldState,
        reenterPasswordTextFieldState = viewModel.reenterPasswordTextFieldState,
        reenterPasswordError = viewModel.reenterPasswordError,
        isLoading = viewModel.isLoading,
        modifier = modifier,
    )
}

@Composable
internal fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onSendRegisterForm: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    emailTextFieldState: TextFieldState,
    passwordTextFieldState: TextFieldState,
    reenterPasswordTextFieldState: TextFieldState,
    reenterPasswordError: LocalizedMessage?,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) = with(sharedTransitionScope) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = LoginRegisterUiDefaults.maxFormHeight)
                .fillMaxSize()
                .padding(LoginRegisterUiDefaults.screenPaddings),
            verticalArrangement = spacedBy(24.dp, alignment = CenterVertically),
            horizontalAlignment = CenterHorizontally,
        ) {
            BaseTextField(
                state = emailTextFieldState,
                label = stringResource(R.string.login_register_field_title_e_mail),
                placeholder = "example@example.ru",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = R.string.login_register_field_title_e_mail),
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
                    .semantics { contentType = ContentType.EmailAddress },
            )

            BaseSecureTextField(
                state = passwordTextFieldState,
                label = stringResource(R.string.login_register_field_title_password),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = R.string.login_register_field_title_password),
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
                    .semantics { contentType = ContentType.Password },
            )

            BaseSecureTextField(
                state = reenterPasswordTextFieldState,
                label = stringResource(R.string.login_register_field_title_reenter_password),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                supportingText = if (reenterPasswordError != null) {
                    stringResource(reenterPasswordError)
                } else {
                    null
                },
                modifier = Modifier.semantics { contentType = ContentType.Password },
            )

            PrimaryActionButton(
                onClick = onSendRegisterForm,
                text = stringResource(R.string.button_register),
                enabled = !isLoading,
                modifier = Modifier
                    .sharedBounds(
                        sharedTransitionScope.rememberSharedContentState("login_register_button"),
                        animatedVisibilityScope = animatedVisibilityScope,
                    ),
            )
            TextButton(
                onClick = onNavigateToLogin,
                enabled = !isLoading,
                modifier = Modifier.align(Alignment.End),
            ) { Text(stringResource(R.string.button_navigate_to_login)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoginScreen() {
    SharedTransitionLayout {
        AnimatedVisibility(true) {
            Coffee1706Theme {
                RegisterScreen(
                    onSendRegisterForm = {},
                    onNavigateToLogin = {},
                    animatedVisibilityScope = this@AnimatedVisibility,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    emailTextFieldState = rememberTextFieldState(),
                    passwordTextFieldState = rememberTextFieldState(),
                    reenterPasswordTextFieldState = rememberTextFieldState(),
                    reenterPasswordError = null,
                    isLoading = false,
                )
            }
        }
    }
}
