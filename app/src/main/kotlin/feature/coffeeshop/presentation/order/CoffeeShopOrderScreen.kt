package com.example.coffe1706.feature.coffeeshop.presentation.order

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.coffe1706.R
import com.example.coffe1706.core.model.MenuItemId
import com.example.coffe1706.core.model.Quantity
import com.example.coffe1706.core.ui.component.CenterAlignedHugeMessage
import com.example.coffe1706.core.ui.component.QuantitySelector
import com.example.coffe1706.core.ui.design.button.PrimaryActionButton
import com.example.coffe1706.core.ui.design.list.Coffee1706ListItemDefaults
import com.example.coffe1706.core.ui.design.list.TwoLineListItem
import com.example.coffe1706.core.ui.internationalization.formatter.price.localizedPrice
import com.example.coffe1706.core.ui.internationalization.message.LocalizedMessage
import com.example.coffe1706.core.ui.internationalization.message.stringResource
import com.example.coffe1706.core.ui.theme3.Coffee1706Theme
import com.example.coffe1706.core.ui.theme3.Coffee1706Typography
import com.example.coffe1706.data.fixtures.MenuItemFixtures

@Composable
internal fun CoffeeShopOrderScreen(
    onCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = hiltViewModel(),
) {
    val state: OrderScreenState by viewModel.state.collectAsStateWithLifecycle()

    when (val currentState = state) {
        OrderScreenState.InitialLoad -> Placeholder(modifier = modifier)
        is OrderScreenState.LoadError -> Error(currentState.errorMessage, modifier = modifier)
        is OrderScreenState.Success -> CoffeeShopOrderScreen(
            items = currentState.menu,
            onQuantityChange = viewModel::setItemQuantity,
            onCheckoutClick = onCheckoutClick,
            modifier = modifier,
        )
    }
}

@Composable
fun CoffeeShopOrderScreen(
    items: List<OrderItemUiModel>,
    onQuantityChange: (MenuItemId, Quantity) -> Unit,
    onCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val listMaxHeight = this.maxHeight / 2
        Column {
            Cart(
                items,
                onQuantityChange = onQuantityChange,
                modifier = Modifier.heightIn(max = listMaxHeight),
            )
            BottomPart(
                onCheckoutClick = onCheckoutClick,
                modifier = Modifier,
                checkoutActive = remember(items) { items.any { it.quantity.value > 0 } }
            )
        }
    }
}

@Composable
private fun Cart(
    items: List<OrderItemUiModel>,
    onQuantityChange: (MenuItemId, Quantity) -> Unit,
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
                        localizedPrice(item.price * item.quantity.value.toBigDecimal()),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                trailingContent = {
                    QuantitySelector(
                        onQuantityChange = onQuantityChange,
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
    checkoutActive: Boolean,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp, bottom = 8.dp)
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
            enabled = checkoutActive,
            modifier = Modifier,
        )
    }
}

@Composable
private fun Placeholder(
    modifier: Modifier = Modifier,
) = Unit

@Composable
private fun Error(
    error: LocalizedMessage,
    modifier: Modifier = Modifier,
) {
    CenterAlignedHugeMessage(
        modifier = modifier.fillMaxSize(),
        text = stringResource(error),
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewCoffeeShopOrderScreen_order() {
    Coffee1706Theme {
        Coffee1706Theme {
            CoffeeShopOrderScreen(
                items = listOf(
                    MenuItemFixtures.espresso.withQuantity(1),
                    MenuItemFixtures.cappuccino.withQuantity(1),
                    MenuItemFixtures.hotChocolate.withQuantity(2),
                    MenuItemFixtures.latte.withQuantity(3),
                    MenuItemFixtures.latte.withQuantity(0).copy(id = MenuItemId(5)),
                    MenuItemFixtures.latte.withQuantity(4).copy(id = MenuItemId(6)),
                ),
                onCheckoutClick = { },
                onQuantityChange = { _, _ -> },
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
            onQuantityChange = { _, _ -> },
            onCheckoutClick = { },
        )
    }
}
