package com.example.coffe1706.core.ui.design.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TwoLineListItem(
    headlineContent: @Composable () -> Unit,
    supportingContent: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier,
    trailingContent: @Composable (() -> Unit)? = null,
    shadowElevation: Dp = Coffee1706ListItemDefaults.shadowElevation,
    colors: ListItemColors = Coffee1706ListItemDefaults.colors(),
) {
    ListItem(
        modifier = modifier,
        headlineContent = headlineContent,
        supportingContent = supportingContent,
        shadowElevation = shadowElevation,
        trailingContent = trailingContent,
        colors = colors,
    )
}

object Coffee1706ListItemDefaults {
    val shadowElevation: Dp = 2.dp
    val verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(6.dp)

    @Composable
    fun colors(): ListItemColors = ListItemDefaults.colors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}
