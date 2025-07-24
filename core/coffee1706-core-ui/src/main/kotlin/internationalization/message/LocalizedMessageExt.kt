package com.example.coffe1706.core.ui.internationalization.message

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
public fun stringResource(message: LocalizedMessage): String = if (message.args.isEmpty()) {
    stringResource(message.resourceId)
} else {
    stringResource(message.resourceId, *message.args.toTypedArray())
}

internal fun Resources.getString(message: LocalizedMessage): String = if (message.args.isEmpty()) {
    getString(message.resourceId)
} else {
    getString(message.resourceId, *message.args.toTypedArray())
}
