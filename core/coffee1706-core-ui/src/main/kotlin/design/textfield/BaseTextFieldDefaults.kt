package com.example.coffe1706.core.ui.design.textfield

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldLabelPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.coffe1706.core.ui.theme.Coffee1706Colors
import com.example.coffe1706.core.ui.theme.Coffee1706Colors.Base.Brown30
import com.example.coffe1706.core.ui.theme.Coffee1706Colors.Base.Brown40Dark

public object BaseTextFieldDefaults {
    public val shape: Shape get() = CircleShape
    public val labelPosition: TextFieldLabelPosition = TextFieldLabelPosition.Above()

    @Composable
    public fun colors(): TextFieldColors = OutlinedTextFieldDefaults.colors(
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
    public fun contentPadding(): PaddingValues = TextFieldDefaults.contentPaddingWithLabel(
        start = 20.dp,
        end = 20.dp,
        top = 8.dp,
        bottom = 8.dp,
    )
}
