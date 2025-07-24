package com.example.coffe1706.data.coffee1706api.session

import kotlinx.coroutines.flow.Flow

public interface Coffee1706AuthManager {
    public val isUserLoggedIn: Flow<Boolean>
}
