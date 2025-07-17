package com.example.coffe1706.core.ui.message

import android.content.res.Resources

internal fun Resources.getString(message: LocalizedMessage): String = if (message.args.isEmpty()) {
    getString(message.resourceId)
} else {
    getString(message.resourceId, *message.args.toTypedArray())
}
