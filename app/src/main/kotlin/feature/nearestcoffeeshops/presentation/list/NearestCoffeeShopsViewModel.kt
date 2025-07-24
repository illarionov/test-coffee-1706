@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)

package com.example.coffe1706.feature.nearestcoffeeshops.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffe1706.R
import com.example.coffe1706.core.model.response.Response
import com.example.coffe1706.core.ui.internationalization.getCommonErrorMessage
import com.example.coffe1706.core.ui.internationalization.message.LocalizedMessage
import com.example.coffe1706.data.location.CurrentLocationDataSource
import com.example.coffe1706.data.location.CurrentLocationStateFlow
import com.example.coffe1706.data.location.CurrentLocationStateFlow.CurrentLocationStatus
import com.example.coffe1706.feature.nearestcoffeeshops.domain.GetNearestLocationsUseCase
import com.example.coffe1706.feature.nearestcoffeeshops.presentation.permission.LocationPermissionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NearestCoffeeShopsViewModel @Inject constructor(
    currentLocationDataSource: CurrentLocationDataSource,
    private val getNearestLocationsUseCase: GetNearestLocationsUseCase,
) : ViewModel() {
    private val permissionsState: MutableStateFlow<LocationPermissionState?> = MutableStateFlow(null)
    private val currentLocationStateFlow = CurrentLocationStateFlow(
        viewModelScope,
        currentLocationDataSource
    )

    init {
        viewModelScope.launch {
            permissionsState.collect {
                if (it != null) {
                    currentLocationStateFlow.setLocationPermissionGranted(it.permissionsGranted)
                }
            }
        }
    }

    val state: StateFlow<NearestCoffeShopsScreenState> = currentLocationStateFlow.flow
        .combine(permissionsState, ::Pair)
        .mapLatest { (locationResult: CurrentLocationStatus, permissions: LocationPermissionState?) ->
            when (locationResult) {
                CurrentLocationStatus.Loading -> NearestCoffeShopsScreenState.InitialLoad
                is CurrentLocationStatus.Available -> {
                    val result = getNearestLocationsUseCase.getNearestLocations(
                        locationResult.coordinates
                    )
                    when (result) {
                        is Response.Success -> NearestCoffeShopsScreenState.Success(locations = result.value)
                        is Response.Failure -> NearestCoffeShopsScreenState.LoadError(result.getCommonErrorMessage())
                    }
                }

                is CurrentLocationStatus.Failure.NoLocationPermissionError -> NearestCoffeShopsScreenState.NoLocationPermission(
                    showApprovePermissionButton = permissions?.shouldShowRationale == true
                )

                is CurrentLocationStatus.Failure.OtherError ->
                    NearestCoffeShopsScreenState.LoadError(
                        LocalizedMessage(R.string.error_failed_to_get_current_location),
                    )
            }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = NearestCoffeShopsScreenState.InitialLoad,
            started = SharingStarted.WhileSubscribed(5000),
        )


    fun setLocationPermissionState(state: LocationPermissionState) {
        permissionsState.value = state
    }
}
