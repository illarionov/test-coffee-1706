package com.example.coffe1706.feature.coffeeshop.presentation.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.coffe1706.R
import com.example.coffe1706.core.model.MenuItemId
import com.example.coffe1706.core.model.Quantity
import com.example.coffe1706.core.ui.component.CenterAlignedHugeMessage
import com.example.coffe1706.core.ui.component.QuantitySelector
import com.example.coffe1706.core.ui.design.button.PrimaryActionButton
import com.example.coffe1706.core.ui.internationalization.formatter.price.localizedPrice
import com.example.coffe1706.core.ui.internationalization.message.LocalizedMessage
import com.example.coffe1706.core.ui.internationalization.message.stringResource
import com.example.coffe1706.core.ui.theme3.Brown20
import com.example.coffe1706.core.ui.theme3.Coffee1706Theme
import com.example.coffe1706.core.ui.theme3.Coffee1706Typography
import com.example.coffe1706.data.fixtures.MenuItemFixtures
import com.example.coffe1706.feature.coffeeshop.presentation.order.OrderItemUiModel
import com.example.coffe1706.feature.coffeeshop.presentation.order.withQuantity

@Composable
internal fun CoffeeShopMenuScreen(
    onCheckout: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CoffeeShopMenuViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (val currentState = state) {
        MenuScreenState.InitialLoad -> Placeholder(modifier)
        is MenuScreenState.LoadError -> Error(currentState.error, modifier)
        is MenuScreenState.Success -> CoffeeShopMenuScreen(
            items = currentState.menu,
            modifier = modifier,
            onQuantityChange = { id: MenuItemId, quantity: Quantity ->
                viewModel.setItemQuantity(id, quantity)
            },
            onCheckout = onCheckout,
        )
    }
}

@Composable
fun CoffeeShopMenuScreen(
    items: List<OrderItemUiModel>,
    onQuantityChange: (MenuItemId, Quantity) -> Unit,
    onCheckout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        if (items.isNotEmpty()) {
            MenuList(
                items = items,
                onQuantityChange = onQuantityChange,
                modifier = Modifier.weight(1f),
            )
        } else {
            CenterAlignedHugeMessage(
                modifier = modifier.weight(1f),
                text = stringResource(R.string.message_no_menu_items),
            )
        }

        val orderIsNotEmpty = remember(items) {
            items.any { it.quantity.value > 0 }
        }

        PrimaryActionButton(
            onClick = onCheckout,
            enabled = orderIsNotEmpty,
            text = stringResource(R.string.button_proceed_to_payment),
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .windowInsetsPadding(WindowInsets.safeContent
                    .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
                    .union(WindowInsets(left = 16.dp, right = 16.dp, bottom = 24.dp)),
            ),
        )
    }
}

@Composable
fun MenuList(
    items: List<OrderItemUiModel>,
    onQuantityChange: (MenuItemId, Quantity) -> Unit,
    modifier: Modifier = Modifier,
) {
    val horizontalInsets = WindowInsets.safeContent
        .only(WindowInsetsSides.Horizontal)
        .union(WindowInsets(left = 16.dp, right = 16.dp))

    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        columns = GridCells.Fixed(2),
        contentPadding = horizontalInsets.asPaddingValues(),
    ) {
        items(
            count = items.size,
            key = { items[it].id.id },
        ) { itemIndex ->
            val item = items[itemIndex]
            MenuItemCard(item, onQuantityChange)
        }
    }
}

private const val COFFEE_CARD_IMAGE_ASPECT_RATIO = 165f / 137f

private val placeholderPainter = ColorPainter(Brown20)
private val errorPainter = ColorPainter(Brown20)

@Composable
private fun MenuItemCard(
    item: OrderItemUiModel,
    onQuantityChange: (MenuItemId, Quantity) -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 2.dp,
            focusedElevation = 2.dp,
            hoveredElevation = 5.dp,
            draggedElevation = 9.dp,
            disabledElevation = 2.dp,
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = MaterialTheme.shapes.small,
    ) {
        Column {
            val imageShape = MaterialTheme.shapes.small.copy(
                bottomStart = ZeroCornerSize,
                bottomEnd = ZeroCornerSize,
            )
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.name,
                placeholder = placeholderPainter,
                error = errorPainter,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(COFFEE_CARD_IMAGE_ASPECT_RATIO)
                    .clip(imageShape),
                contentScale = ContentScale.Crop,
            )

            Text(
                text = item.name,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = Coffee1706Typography.bodyMediumVariant,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                    .semantics { heading() },
            )

            Row(
                modifier = Modifier.padding(bottom = 8.dp, start = 10.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = localizedPrice(item.price),
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = Coffee1706Typography.supportingTextBold,
                    modifier = Modifier
                        .padding(end = 2.dp)
                        .weight(1f),
                )
                QuantitySelector(onQuantityChange, item.id, item.quantity)
            }
        }
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
private fun PreviewCoffeeShopMenuScreen_with_items() {
    Coffee1706Theme {
        CoffeeShopMenuScreen(
            items = emptyList(),
            onQuantityChange = { _, _ -> },
            onCheckout = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCoffeeShopMenuScreen_empty_list() {
    Coffee1706Theme {
        CoffeeShopMenuScreen(
            items = listOf(
                MenuItemFixtures.espresso.withQuantity(0),
                MenuItemFixtures.cappuccino.withQuantity(1),
                MenuItemFixtures.hotChocolate.withQuantity(2),
                MenuItemFixtures.latte.withQuantity(0),
            ),
            onQuantityChange = { _, _ -> },
            onCheckout = {},
        )
    }
}
