package com.example.coffe1706.core.ui.component.snackbar

import android.annotation.SuppressLint
import android.content.res.Resources
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.example.coffe1706.core.ui.internationalization.message.LocalizedMessage
import com.example.coffe1706.core.ui.internationalization.message.getString
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.isActive
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SnackbarController @Inject constructor() {
    private val channel: Channel<LocalizedMessage> = Channel(
        capacity = Channel.UNLIMITED,
    )
    val receiveChannel: ReceiveChannel<LocalizedMessage> = channel

    suspend fun enqueueMessage(message: LocalizedMessage) {
        channel.send(message)
    }
}

@SuppressLint("ComposableNaming")
@Composable
fun subscribeToSnackbarsFlowWithLifecycle(
    snackbarController: SnackbarController,
    snackbarHostState: SnackbarHostState,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    resources: Resources = LocalContext.current.resources
) {
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            while (coroutineContext.isActive) {
                val msg: LocalizedMessage = snackbarController.receiveChannel.receive()
                snackbarHostState.showSnackbar(resources.getString(msg))
            }
        }
    }
}
