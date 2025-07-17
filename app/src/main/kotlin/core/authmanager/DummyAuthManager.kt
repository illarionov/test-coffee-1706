package com.example.coffe1706.core.authmanager

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DummyAuthManager(isLoggedIn: Boolean = false) : AuthManager {
    override val isUserLoggedIn: StateFlow<Boolean> = MutableStateFlow(isLoggedIn)
}
