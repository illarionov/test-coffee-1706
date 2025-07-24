package com.example.coffe1706.data.location

import com.example.coffe1706.core.model.LatLon
import com.example.coffe1706.data.location.CurrentLocationDataSource.LocationResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

/**
 * Вспомогательный класс со StateFlow с текущем местоположением.
 *
 * Подписывается на [currentLocationDataSource], когда было выдно разрешение на местоположение
 * и в [flow] эммитит обновления.
 *
 * Требует ручного вызова setLocationPermissionGranted(), когда изменился статус разрешения местоположения
 */
public class CurrentLocationStateFlow(
    private val scope: CoroutineScope,
    private val currentLocationDataSource: CurrentLocationDataSource,
    private val started: SharingStarted = SharingStarted.WhileSubscribed(5000),
) {
    private val permissionGranted: MutableStateFlow<PermissionGranted> = MutableStateFlow(PermissionGranted.UNKNOWN)

    @OptIn(ExperimentalCoroutinesApi::class)
    public val flow: StateFlow<CurrentLocationStatus> = permissionGranted
        .flatMapLatest { permistionState ->
            when (permistionState) {
                PermissionGranted.UNKNOWN -> flowOf(CurrentLocationStatus.Loading)
                PermissionGranted.NO -> flowOf(CurrentLocationStatus.Failure.NoLocationPermissionError)
                PermissionGranted.YES -> currentLocationDataSource.getCurrentLocationFlow()
                    .map { it.toCurrentLocationStatus() }
                    .onStart { emit(CurrentLocationStatus.Loading) }
            }
        }
        .stateIn(
            scope = scope,
            initialValue = CurrentLocationStatus.Loading,
            started = started,
        )

    public fun setLocationPermissionGranted(granted: Boolean) {
        permissionGranted.value = if (granted) PermissionGranted.YES else PermissionGranted.NO
    }

    public sealed interface CurrentLocationStatus {
        public object Loading : CurrentLocationStatus
        public data class Available(val coordinates: LatLon) : CurrentLocationStatus
        public sealed class Failure : CurrentLocationStatus {
            public object NoLocationPermissionError : Failure()
            public data class OtherError(val error: Throwable) : Failure()
        }
    }

    private enum class PermissionGranted { YES, NO, UNKNOWN }

    private companion object {
        private fun LocationResult.toCurrentLocationStatus(): CurrentLocationStatus = when (this) {
            is LocationResult.Success -> CurrentLocationStatus.Available(this.coordinates)
            LocationResult.Failure.NoLocationPermissionError -> CurrentLocationStatus.Failure.NoLocationPermissionError
            is LocationResult.Failure.OtherError -> CurrentLocationStatus.Failure.OtherError(
                this.error,
            )
        }
    }
}
