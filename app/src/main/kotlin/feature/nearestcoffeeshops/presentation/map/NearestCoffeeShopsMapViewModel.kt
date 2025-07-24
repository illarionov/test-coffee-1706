@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.coffe1706.feature.nearestcoffeeshops.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffe1706.core.model.LatLon
import com.example.coffe1706.core.model.Location
import com.example.coffe1706.core.model.response.Response
import com.example.coffe1706.core.ui.component.snackbar.SnackbarController
import com.example.coffe1706.core.ui.internationalization.getCommonErrorMessage
import com.example.coffe1706.feature.nearestcoffeeshops.data.NearestLocationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NearestCoffeeShopsMapViewModel @Inject constructor(
    private val nearestLocationsRepository: NearestLocationsRepository,
    private val snackbarController: SnackbarController,
) : ViewModel() {
    internal val state: StateFlow<NearestCoffeeShopsMapScreenState> = flowOf<LatLon?>(null)
        .mapLatest { location ->
            val response: Response<List<Location>> = nearestLocationsRepository.getNearestLocations(location)
            when (response) {
                is Response.Failure -> NearestCoffeeShopsMapScreenState.LoadError(
                    response.getCommonErrorMessage()
                )
                is Response.Success<List<Location>> -> NearestCoffeeShopsMapScreenState.Success(
                    response.value
                )
            }
        }
        .stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(5000), NearestCoffeeShopsMapScreenState.InitialLoad)

    init {
        viewModelScope.launch {
            val firstError = state
                .filterIsInstance<NearestCoffeeShopsMapScreenState.LoadError>()
                .firstOrNull()
            if (firstError != null) {
                snackbarController.enqueueMessage(firstError.error)
            }
        }
    }
}
