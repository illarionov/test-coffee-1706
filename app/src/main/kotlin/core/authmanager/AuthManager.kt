package com.example.coffe1706.core.authmanager

import kotlinx.coroutines.flow.Flow

interface AuthManager {
    val isUserLoggedIn: Flow<Boolean>
}
