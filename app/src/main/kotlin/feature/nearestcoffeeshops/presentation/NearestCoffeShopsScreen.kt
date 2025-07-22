package com.example.coffe1706.feature.nearestcoffeeshops.presentation

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.coffe1706.R
import com.example.coffe1706.core.model.LocationId
import com.example.coffe1706.core.ui.component.CenterAlignedHugeMessage
import com.example.coffe1706.core.ui.design.button.PrimaryActionButton
import com.example.coffe1706.core.ui.design.list.Coffee1706ListItemDefaults
import com.example.coffe1706.core.ui.design.list.TwoLineListItem
import com.example.coffe1706.core.ui.internationalization.formatter.distance.localizedMessage
import com.example.coffe1706.core.ui.internationalization.message.LocalizedMessage
import com.example.coffe1706.core.ui.internationalization.message.stringResource
import com.example.coffe1706.core.ui.theme3.Coffee1706Theme
import com.example.coffe1706.core.ui.theme3.Coffee1706Typography
import com.example.coffe1706.data.fixtures.NearestLocationFixtures
import com.example.coffe1706.feature.nearestcoffeeshops.domain.NearestLocation
import com.example.coffe1706.feature.nearestcoffeeshops.presentation.permission.LocationPermissionState
import com.example.coffe1706.feature.nearestcoffeeshops.presentation.permission.LocationPermissionState.Companion.locationPermissions

@Composable
internal fun NearestCoffeeShopsScreen(
    onLocationClick: (id: LocationId) -> Unit,
    onShowOnMapClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NearestCoffeeShopsViewModel = hiltViewModel(),
) {
    val activity: Activity = checkNotNull(LocalActivity.current) { "activity note set" }
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions: Map<String, Boolean> ->
            if (permissions.keys.containsAll(locationPermissions.toList())) {
                viewModel.setLocationPermissionState(LocationPermissionState.get(activity))
            }
        },
    )


    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START) {
                    val permissionState = LocationPermissionState.get(activity)
                    viewModel.setLocationPermissionState(permissionState)

                    if (!permissionState.permissionsGranted && !permissionState.shouldShowRationale) {
                        locationPermissionLauncher.launch(locationPermissions)
                    }
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        },
    )

    val state by viewModel.state.collectAsStateWithLifecycle()
    NearestCoffeeShopsScreen(
        onLocationClick = onLocationClick,
        onApprovePermissionClick = {
            locationPermissionLauncher.launch(locationPermissions)
        },
        onShowOnMapClick = onShowOnMapClick,
        state = state,
        modifier = modifier,
    )
}

@Composable
internal fun NearestCoffeeShopsScreen(
    onLocationClick: (id: LocationId) -> Unit,
    onApprovePermissionClick: () -> Unit,
    onShowOnMapClick: () -> Unit,
    state: NearestCoffeShopsScreenState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Box(
            modifier = modifier.weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                is NearestCoffeShopsScreenState.NoLocationPermission -> NoLocationPermission(
                    showApprovePermissionButton = state.showApprovePermissionButton,
                    onApprovePermissionClick = onApprovePermissionClick,
                )

                NearestCoffeShopsScreenState.InitialLoad -> InitialLoad()
                is NearestCoffeShopsScreenState.LoadError -> LoadError(
                    error = state.error,
                )

                is NearestCoffeShopsScreenState.Success -> NearestLocations(
                    onLocationClick = onLocationClick,
                    locations = state.locations,
                )
            }
        }

        val showShowOnMapButton = when (state) {
            NearestCoffeShopsScreenState.InitialLoad -> true
            is NearestCoffeShopsScreenState.LoadError -> true
            is NearestCoffeShopsScreenState.NoLocationPermission -> false
            is NearestCoffeShopsScreenState.Success -> true
        }

        if (showShowOnMapButton) {
            PrimaryActionButton(
                onClick = onShowOnMapClick,
                text = stringResource(R.string.button_show_on_map),
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.safeContent
                        .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
                        .union(WindowInsets(left = 16.dp, right = 16.dp, bottom = 24.dp, top = 8.dp)),
                ),
            )
        }
    }
}

@Composable
private fun NoLocationPermission(
    showApprovePermissionButton: Boolean,
    onApprovePermissionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.location_permission_required),
            style = Coffee1706Typography.bodyLargeTextHuge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .widthIn(max = 360.dp)
                .padding(horizontal = 24.dp, vertical = 24.dp),
        )
        if (showApprovePermissionButton) {
            PrimaryActionButton(
                onClick = onApprovePermissionClick,
                text = stringResource(R.string.button_approve_location_permission),
                modifier = Modifier
                    .widthIn(max = 360.dp)
                    .padding(horizontal = 24.dp, vertical = 24.dp),
            )
        }
    }
}

@Composable
private fun InitialLoad(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
private fun LoadError(
    error: LocalizedMessage,
    modifier: Modifier = Modifier,
) {
    CenterAlignedHugeMessage(
        modifier = modifier.fillMaxSize(),
        text = stringResource(error),
    )
}


@Composable
private fun NearestLocations(
    onLocationClick: (id: LocationId) -> Unit,
    locations: List<NearestLocation>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        if (locations.isNotEmpty()) {
            NearestLocationLazyList(
                locations = locations,
                onLocationClick = onLocationClick,
                modifier = Modifier.weight(1f),
            )
        } else {
            CenterAlignedHugeMessage(
                modifier = modifier.weight(1f),
                text = stringResource(R.string.message_no_coffee_shops_nearby),
            )
        }

    }
}

@Composable
private fun NearestLocationLazyList(
    locations: List<NearestLocation>,
    onLocationClick: (LocationId) -> Unit,
    modifier: Modifier = Modifier,
) {
    val horizontalInsets = WindowInsets.safeContent
        .only(WindowInsetsSides.Horizontal)
        .union(WindowInsets(left = 16.dp, right = 16.dp))

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp),
        verticalArrangement = Coffee1706ListItemDefaults.verticalArrangement,
    ) {
        items(
            count = locations.size,
            key = { locations[it].id.id },
        ) { itemIndex ->
            val location = locations[itemIndex]
            TwoLineListItem(
                modifier = Modifier
                    .clickable(
                        onClick = { onLocationClick(location.id) },
                    )
                    .windowInsetsPadding(horizontalInsets),
                headlineContent = {
                    Text(
                        location.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = Coffee1706Typography.bodyLargeTextBold,
                    )
                },
                supportingContent = {
                    Text(
                        localizedMessage(location.distance),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewNearestCoffeeShopsScreen_with_locations() {
    Coffee1706Theme {
        NearestCoffeeShopsScreen(
            onLocationClick = {},
            onShowOnMapClick = {},
            onApprovePermissionClick = {},
            state = NearestCoffeShopsScreenState.Success(
                locations = listOf(
                    NearestLocationFixtures.bedoefCoffee1,
                    NearestLocationFixtures.coffeeLike,
                    NearestLocationFixtures.emdi,
                    NearestLocationFixtures.coffeEst,
                    NearestLocationFixtures.bedoefCoffee2,
                ),
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewNearestCoffeeShopsScreen_empty_list() {
    Coffee1706Theme {
        NearestCoffeeShopsScreen(
            onLocationClick = {},
            onShowOnMapClick = {},
            onApprovePermissionClick = {},
            state = NearestCoffeShopsScreenState.Success(
                locations = emptyList(),
            ),
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewNearestCoffeeShopsScreen_initial_load() {
    Coffee1706Theme {
        NearestCoffeeShopsScreen(
            onLocationClick = {},
            onShowOnMapClick = {},
            onApprovePermissionClick = {},
            state = NearestCoffeShopsScreenState.InitialLoad,
        )
    }
}
