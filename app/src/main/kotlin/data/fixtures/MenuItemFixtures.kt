package com.example.coffe1706.data.fixtures

import com.example.coffe1706.core.model.MenuItem
import com.example.coffe1706.core.model.MenuItemId

object MenuItemFixtures {
    val espresso = MenuItem(
        MenuItemId(1),
        "Эспрессо",
        "https://images.unsplash.com/photo-1596952954288-16862d37405b",
        200.toBigDecimal()
        )
    val cappuccino = MenuItem(
        MenuItemId(2),
        "Капучино",
        "https://plus.unsplash.com/premium_photo-1669374537636-518629de3b85",
        200.toBigDecimal()
    )
    val hotChocolate = MenuItem(
        MenuItemId(3),
        "Горячий шоколад",
        "https://images.unsplash.com/photo-1517578239113-b03992dcdd25",
        200.toBigDecimal()
    )
    val latte = MenuItem(
        MenuItemId(4),
        "Латте",
        "https://plus.unsplash.com/premium_photo-1674327105280-b86494dfc690",
        200.toBigDecimal()
    )
}
