package com.example.coffe1706.core.ui.design.textfield

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults.contentPaddingWithLabel
import androidx.compose.material3.TextFieldLabelPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.coffe1706.core.ui.theme3.Brown30
import com.example.coffe1706.core.ui.theme3.Brown40Dark
import com.example.coffe1706.core.ui.theme3.Coffee1706Colors

@Composable
fun BaseTextField(
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
fun BaseSecureTextField(
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
fun PrimaryTextFieldLabel(
    label: String,
    modifier: Modifier = Modifier,
) {
    Text(
        label,
        modifier = modifier.padding(bottom = 2.dp),
    )
}

@Composable
fun BaseTextFieldPlaceholder(
    label: String,
    modifier: Modifier = Modifier,
) {
    Text(
        label,
        modifier = modifier,
    )
}

@Composable
fun BaseTextFieldSupportingText(
    label: String,
    modifier: Modifier = Modifier,
) {
    Text(
        label,
        modifier = modifier,
    )
}

object BaseTextFieldDefaults {
    val shape: Shape get() = CircleShape
    val labelPosition: TextFieldLabelPosition = TextFieldLabelPosition.Above()

    @Composable
    fun colors(): TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Brown40Dark,
        unfocusedBorderColor = Coffee1706Colors.TextColor,
        disabledBorderColor = Brown30,
        focusedLabelColor = Brown40Dark,
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
        focusedTextColor = Brown40Dark,
        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    @Composable
    fun contentPadding() = contentPaddingWithLabel(
        start = 20.dp,
        end = 20.dp,
        top = 8.dp,
        bottom = 8.dp,
    )
}
