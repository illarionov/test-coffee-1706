package com.example.coffe1706.feature.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldLabelPosition
import androidx.compose.runtime.Composable
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
import com.example.coffe1706.R
import com.example.coffe1706.core.ui.button.PrimaryActionButton
import com.example.coffe1706.core.ui.theme3.Coffee1706Theme

@Composable
internal fun LoginScreen(
    onRegister: () -> Unit,
    onLogin: () -> Unit,
    modifier: Modifier = Modifier,
    emailTextFieldState: TextFieldState = rememberTextFieldState(),
    passwordTextFieldState: TextFieldState = rememberTextFieldState(),
) {
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
                .widthIn(max = 370.dp)
                .fillMaxSize()
                .padding(horizontal = 18.dp, vertical = 24.dp),
            verticalArrangement = spacedBy(24.dp, alignment = CenterVertically),
            horizontalAlignment = CenterHorizontally,
        ) {
            OutlinedTextField(
                state = emailTextFieldState,
                label = {
                    Text(stringResource(R.string.login_register_field_title_e_mail))
                },
                labelPosition = TextFieldLabelPosition.Above(),
                shape = CircleShape,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentType = ContentType.EmailAddress },
            )
            OutlinedTextField(
                state = passwordTextFieldState,
                label = {
                    Text(stringResource(R.string.login_register_field_title_password))
                },
                labelPosition = TextFieldLabelPosition.Above(),
                shape = CircleShape,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentType = ContentType.Password },
            )

            PrimaryActionButton(
                onClick = onLogin,
                text = stringResource(R.string.button_login),
            )
            TextButton(
                onClick = onRegister,
                modifier = Modifier.align(Alignment.End),
            ) { Text(stringResource(R.string.button_register)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoginScreen() {
    Coffee1706Theme {
        LoginScreen(
            onRegister = {},
            onLogin = {},
        )
    }
}
