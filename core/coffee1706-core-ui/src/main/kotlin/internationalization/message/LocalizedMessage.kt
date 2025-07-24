package com.example.coffe1706.core.ui.internationalization.message

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
public data class LocalizedMessage(
    @param:StringRes val resourceId: Int,
    val args: List<Any> = emptyList(),
)
