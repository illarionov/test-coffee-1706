package com.example.coffe1706.feature.nearestcoffeeshops.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coffe1706.R
import com.example.coffe1706.core.model.LocationId
import com.example.coffe1706.core.ui.design.button.PrimaryActionButton
import com.example.coffe1706.core.ui.design.formatter.distance.localizedMessage
import com.example.coffe1706.core.ui.design.list.Coffee1706ListItemDefaults
import com.example.coffe1706.core.ui.design.list.TwoLineListItem
import com.example.coffe1706.core.ui.component.CenterAlignedHugeMessage
import com.example.coffe1706.core.ui.theme3.Coffee1706Theme
import com.example.coffe1706.core.ui.theme3.Coffee1706Typography
import com.example.coffe1706.data.fixtures.NearestLocationFixtures

@Composable
fun NearestCoffeeShopsScreen(
    onLocationClick: (id: LocationId) -> Unit,
    onShowOnMapClick: () -> Unit,
    locations: List<NearestLocation>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        if (locations.isNotEmpty()) {
            NearestLocationList(
                locations = locations,
                onLocationClick = onLocationClick,
                modifier = Modifier.weight(1f),
            )
        } else {
            CenterAlignedHugeMessage(
                modifier = modifier.weight(1f),
                text = stringResource(R.string.message_no_coffee_shops_nearby)
            )
        }
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

@Composable
private fun NearestLocationList(
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
                        style = Coffee1706Typography.bodyLargeTextBold
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

@Composable
private fun EmptyLocationList(
    modifier: Modifier = Modifier,
) {

}

@Preview(showBackground = true)
@Composable
private fun PreviewNearestCoffeeShopsScreen_with_locations() {
    Coffee1706Theme {
        NearestCoffeeShopsScreen(
            onLocationClick = {},
            onShowOnMapClick = {},
            locations = listOf(
                NearestLocationFixtures.bedoefCoffee1,
                NearestLocationFixtures.coffeeLike,
                NearestLocationFixtures.emdi,
                NearestLocationFixtures.coffeEst,
                NearestLocationFixtures.bedoefCoffee2,
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
            locations = emptyList(),
        )
    }
}
