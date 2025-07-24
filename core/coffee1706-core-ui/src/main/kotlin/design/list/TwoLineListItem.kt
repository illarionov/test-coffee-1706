package com.example.coffe1706.core.ui.design.list

import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
public fun TwoLineListItem(
    headlineContent: @Composable () -> Unit,
    supportingContent: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier,
    trailingContent: @Composable (() -> Unit)? = null,
    shadowElevation: Dp = TwoLineListItemDefaults.shadowElevation,
    colors: ListItemColors = TwoLineListItemDefaults.colors(),
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

