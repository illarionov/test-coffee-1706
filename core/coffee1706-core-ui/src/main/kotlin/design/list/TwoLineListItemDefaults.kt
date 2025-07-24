package com.example.coffe1706.core.ui.design.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

public object TwoLineListItemDefaults {
    public val shadowElevation: Dp = 2.dp
    public val verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(6.dp)

    @Composable
    public fun colors(): ListItemColors = ListItemDefaults.colors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}
