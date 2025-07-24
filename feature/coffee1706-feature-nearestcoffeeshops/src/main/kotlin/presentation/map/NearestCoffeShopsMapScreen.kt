package com.example.coffe1706.feature.nearestcoffeeshops.presentation.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.coffe1706.core.model.LatLon
import com.example.coffe1706.core.model.LocationId
import com.example.coffe1706.core.ui.theme.Coffee1706Colors
import com.example.coffe1706.feature.nearestcoffeeshops.R
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

private val PLACEMARK_TEXT_STYLE = TextStyle(
    /* size = */ 18f,
    /* color = */ Coffee1706Colors.TextColor.toArgb(),
    /* outlineWidth = */ 0f,
    /* outlineColor = */ Coffee1706Colors.TextColor.toArgb(),
    /* placement = */ TextStyle.Placement.BOTTOM,
    /* offset = */ 0f,
    /* offsetFromIcon = */ true,
    /* textOptional = */ false,
)

@Composable
internal fun NearestCoffeShopsMapScreen(
    onLocationClick: (id: LocationId) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NearestCoffeeShopsMapViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    NearestCoffeShopsMapScreen(
        onLocationClick = onLocationClick,
        state = state,
        modifier = modifier,
    )
}

@Composable
internal fun NearestCoffeShopsMapScreen(
    onLocationClick: (id: LocationId) -> Unit,
    state: NearestCoffeeShopsMapScreenState,
    modifier: Modifier = Modifier,
) {
    val mapView = remember { mutableStateOf<MapView?>(null) }

    val placemarkTapListener = remember {
        MapObjectTapListener { mapObject, point ->
            onLocationClick(mapObject.userData as LocationId)
            true
        }
    }
    val viewContext = LocalView.current.context
    val imageProvider = remember(viewContext) {
        ImageProvider.fromResource(viewContext, R.drawable.ic_coffee_shop_placemark)
    }

    AndroidView(
        factory = { MapView(it) },
        update = { map: MapView ->
            mapView.value = map
            when (state) {
                NearestCoffeeShopsMapScreenState.InitialLoad -> {}
                is NearestCoffeeShopsMapScreenState.LoadError -> {}
                is NearestCoffeeShopsMapScreenState.Success -> {
                    val locations = state.locations
                    val submap = map.mapWindow.map

                    if (locations.isNotEmpty()) {
                        val position = locations[0].position
                        submap.move(position.toCameraPosition())
                    }
                    locations.forEach { location ->
                        submap.mapObjects.addPlacemark().apply {
                            geometry = location.position.toMapkitPoint()
                            setIcon(imageProvider)
                            setText(location.name, PLACEMARK_TEXT_STYLE)
                            userData = location.id
                            addTapListener(placemarkTapListener)
                        }
                    }
                }
            }
        },
        onReset = { view: MapView ->
            view.mapWindow.map.mapObjects.clear()
        },
        onRelease = { view: MapView ->
            view.mapWindow.map.mapObjects.clear()
            view.onStop()
        },
        modifier = modifier.fillMaxSize(),
    )

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = YandexMapsEventObserver { mapView.value }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        },
    )
}

private fun LatLon.toMapkitPoint(): Point = Point(this.latitude, this.longitude)

private fun LatLon.toCameraPosition(): CameraPosition = CameraPosition(
    /* target = */ this.toMapkitPoint(),
    /* zoom = */ 17.0f,
    /* azimuth = */ 0f,
    /* tilt = */ 0f,
)

private class YandexMapsEventObserver(
    private val mapView: () -> MapView?,
) : LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> {
                MapKitFactory.getInstance().onStart()
                mapView()?.onStart()
            }

            Lifecycle.Event.ON_STOP -> {
                MapKitFactory.getInstance().onStop()
                mapView()?.onStart()
            }

            else -> Unit
        }
    }
}
