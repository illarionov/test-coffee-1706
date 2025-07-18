package com.example.coffe1706.core.authmanager

import kotlinx.coroutines.flow.Flow

// TODO: remove?
interface AuthManager {
    val isUserLoggedIn: Flow<Boolean>
}
