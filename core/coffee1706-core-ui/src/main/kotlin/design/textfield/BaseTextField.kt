package com.example.coffe1706.core.ui.design.textfield

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
public fun BaseTextField(
    state: TextFieldState,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    colors: TextFieldColors = BaseTextFieldDefaults.colors(),
    contentPadding: PaddingValues = BaseTextFieldDefaults.contentPadding(),
) {
    OutlinedTextField(
        state = state,
        label = {
            PrimaryTextFieldLabel(label)
        },
        placeholder = if (placeholder != null) {
            { BaseTextFieldPlaceholder(placeholder) }
        } else {
            null
        },
        labelPosition = BaseTextFieldDefaults.labelPosition,
        shape = BaseTextFieldDefaults.shape,
        keyboardOptions = keyboardOptions,
        colors = colors,
        contentPadding = contentPadding,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp),
    )
}

@Composable
public fun BaseSecureTextField(
    state: TextFieldState,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    colors: TextFieldColors = BaseTextFieldDefaults.colors(),
    contentPadding: PaddingValues = BaseTextFieldDefaults.contentPadding(),
) {
    OutlinedSecureTextField(
        state = state,
        label = { PrimaryTextFieldLabel(label) },
        placeholder = if (placeholder != null) {
            { BaseTextFieldPlaceholder(placeholder) }
        } else {
            null
        },
        supportingText = if (supportingText != null) {
            {
                BaseTextFieldSupportingText(supportingText)
            }
        } else {
            null
        },
        labelPosition = BaseTextFieldDefaults.labelPosition,
        shape = BaseTextFieldDefaults.shape,
        isError = isError,
        keyboardOptions = keyboardOptions,
        colors = colors,
        contentPadding = contentPadding,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp),
    )
}

@Composable
internal fun PrimaryTextFieldLabel(
    label: String,
    modifier: Modifier = Modifier,
) {
    Text(
        label,
        modifier = modifier.padding(bottom = 2.dp),
    )
}

@Composable
internal fun BaseTextFieldPlaceholder(
    label: String,
    modifier: Modifier = Modifier,
) {
    Text(
        label,
        modifier = modifier,
    )
}

@Composable
internal fun BaseTextFieldSupportingText(
    label: String,
    modifier: Modifier = Modifier,
) {
    Text(
        label,
        modifier = modifier,
    )
}

