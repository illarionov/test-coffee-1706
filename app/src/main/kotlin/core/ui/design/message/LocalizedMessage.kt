package com.example.coffe1706.core.ui.design.message

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
data class LocalizedMessage(
    @param:StringRes val resourceId: Int,
    val args: List<Any> = emptyList(),
)
