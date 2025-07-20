package com.example.coffe1706.feature.coffeeshop.presentation

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffe1706.R
import com.example.coffe1706.core.model.MenuItemId
import com.example.coffe1706.core.ui.component.CenterAlignedHugeMessage
import com.example.coffe1706.core.ui.components.QuantitySelector
import com.example.coffe1706.core.ui.design.button.PrimaryActionButton
import com.example.coffe1706.core.ui.design.list.Coffee1706ListItemDefaults
import com.example.coffe1706.core.ui.design.list.TwoLineListItem
import com.example.coffe1706.core.ui.internationalization.formatter.price.localizedPrice
import com.example.coffe1706.core.ui.theme3.Coffee1706Theme
import com.example.coffe1706.core.ui.theme3.Coffee1706Typography
import com.example.coffe1706.data.fixtures.MenuItemFixtures

@Composable
fun CoffeeShopOrderScreen(
    items: List<OrderCartItem>,
    onItemCountChange: (MenuItemId, Int) -> Unit,
    onCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val listMaxHeight = this.maxHeight / 2
        Column {
            Cart(
                items,
                onItemCountChange = onItemCountChange,
                modifier = Modifier.heightIn(max = listMaxHeight),
            )
            BottomPart(
                onCheckoutClick = onCheckoutClick,
                modifier = Modifier,
            )
        }
    }
}

@Composable
private fun Cart(
    items: List<OrderCartItem>,
    onItemCountChange: (MenuItemId, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val horizontalInsets = WindowInsets.safeContent
        .only(WindowInsetsSides.Horizontal)
        .union(WindowInsets(left = 16.dp, right = 16.dp))

    LazyColumn(
        modifier = modifier.padding(top = 16.dp),
        verticalArrangement = Coffee1706ListItemDefaults.verticalArrangement,
    ) {
        items(
            count = items.size,
            key = { items[it].id.id },
        ) { itemIndex ->
            val item = items[itemIndex]
            TwoLineListItem(
                modifier = Modifier.windowInsetsPadding(horizontalInsets),
                headlineContent = {
                    Text(
                        item.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = Coffee1706Typography.bodyLargeTextBold,
                    )
                },
                supportingContent = {
                    Text(
                        localizedPrice(item.price * item.quantity.toBigDecimal()),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                trailingContent = {
                    QuantitySelector(
                        onItemCountChange = onItemCountChange,
                        menuItemId = item.id,
                        quantity = item.quantity,
                        buttonsColor = MaterialTheme.colorScheme.onSurface,
                        textStyle = Coffee1706Typography.supportingTextBold.copy(fontSize = 16.sp),
                        textMinWidth = 24.dp,
                    )
                },
            )
        }
    }
}

@Composable
private fun BottomPart(
    modifier: Modifier = Modifier,
    onCheckoutClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(
                WindowInsets.safeContent
                    .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
                    .union(WindowInsets(left = 16.dp, right = 16.dp, bottom = 24.dp)),
            ),
    ) {
        CenterAlignedHugeMessage(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.estimated_time_thank_for_choosing_us),
        )
        PrimaryActionButton(
            onClick = onCheckoutClick,
            text = stringResource(R.string.button_checkout),
            modifier = Modifier,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCoffeeShopOrderScreen_order() {
    Coffee1706Theme {
        Coffee1706Theme {
            CoffeeShopOrderScreen(
                items = listOf(
                    MenuItemFixtures.espresso.withCount(1),
                    MenuItemFixtures.cappuccino.withCount(1),
                    MenuItemFixtures.hotChocolate.withCount(2),
                    MenuItemFixtures.latte.withCount(3),
                    MenuItemFixtures.latte.withCount(0).copy(id = MenuItemId("5")),
                    MenuItemFixtures.latte.withCount(4).copy(id = MenuItemId("6")),
                ),
                onItemCountChange = { _, _ -> },
                onCheckoutClick = { },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCoffeeShopOrderScreen_empty_list() {
    Coffee1706Theme {
        CoffeeShopOrderScreen(
            items = emptyList(),
            onItemCountChange = { _, _ -> },
            onCheckoutClick = { },
        )
    }
}
