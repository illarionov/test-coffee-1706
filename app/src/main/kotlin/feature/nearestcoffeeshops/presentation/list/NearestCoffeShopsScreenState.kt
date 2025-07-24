package com.example.coffe1706.feature.nearestcoffeeshops.presentation.list

import com.example.coffe1706.core.ui.internationalization.message.LocalizedMessage
import com.example.coffe1706.feature.nearestcoffeeshops.domain.NearestLocation

internal sealed interface NearestCoffeShopsScreenState {
    object InitialLoad : NearestCoffeShopsScreenState
    data class NoLocationPermission(
        val showApprovePermissionButton: Boolean,
    ) : NearestCoffeShopsScreenState
    data class LoadError(
        val error: LocalizedMessage
    ) : NearestCoffeShopsScreenState
    data class Success(
        val locations: List<NearestLocation>,
    ) : NearestCoffeShopsScreenState
}
