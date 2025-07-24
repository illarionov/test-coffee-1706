package com.example.coffe1706.feature.auth.presentation.login

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
import com.example.coffe1706.core.ui.theme.Coffee1706Theme
import com.example.coffe1706.feature.auth.R
import com.example.coffe1706.feature.auth.presentation.LoginRegisterUiDefaults

@Composable
internal fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel.labels) {
        viewModel.labels.collect { label ->
            when (label) {
                LoginViewModel.Label.LoginSuccess -> onLoginSuccess()
            }
        }
    }

    LoginScreen(
        onNavigateToRegister = onNavigateToRegister,
        onLogin = viewModel::login,
        animatedVisibilityScope = animatedVisibilityScope,
        sharedTransitionScope = sharedTransitionScope,
        emailTextFieldState = viewModel.emailTextFieldState,
        passwordTextFieldState = viewModel.passwordTextFieldState,
        isLoading = viewModel.isLoading,
        modifier = modifier,
    )
}

@Composable
internal fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLogin: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    emailTextFieldState: TextFieldState,
    passwordTextFieldState: TextFieldState,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
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
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = R.string.login_register_field_title_password),
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
                    .semantics { contentType = ContentType.Password },
            )

            PrimaryActionButton(
                onClick = onLogin,
                text = stringResource(R.string.button_login),
                enabled = !isLoading,
                modifier = Modifier
                    .sharedBounds(
                        sharedTransitionScope.rememberSharedContentState("login_register_button"),
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
            )
            TextButton(
                onClick = onNavigateToRegister,
                modifier = Modifier.align(Alignment.End),
                enabled = !isLoading
            ) { Text(stringResource(R.string.button_navigate_to_register)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoginScreen() {
    SharedTransitionLayout {
        AnimatedVisibility(true) {
            Coffee1706Theme {
                LoginScreen(
                    onNavigateToRegister = {},
                    onLogin = {},
                    animatedVisibilityScope = this@AnimatedVisibility,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    emailTextFieldState = rememberTextFieldState(),
                    passwordTextFieldState = rememberTextFieldState(),
                    isLoading = false,
                )
            }
        }
    }
}
