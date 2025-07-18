package com.example.coffe1706.core.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.coffe1706.R
import com.example.coffe1706.core.model.MenuItemId
import com.example.coffe1706.core.ui.icons.Remove
import com.example.coffe1706.core.ui.theme3.Coffee1706Colors
import com.example.coffe1706.core.ui.theme3.Coffee1706Typography

@Composable
fun QuantitySelector(
    onItemCountChange: (MenuItemId, Int) -> Unit,
    menuItemId: MenuItemId,
    quantity: Int,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    textStyle: TextStyle = Coffee1706Typography.supportingTextNormal,
    textMinWidth: Dp = 12.dp,
    buttonsColor: Color = Coffee1706Colors.TextColorLighterLighter,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SmallIconButton(
            enabled = quantity > 0,
            onClick = { onItemCountChange(menuItemId, -1) },
            imageVector = Icons.Default.Remove,
            contentDescription = stringResource(R.string.remove_from_cart),
            color = buttonsColor,
        )

        Text(
            text = quantity.toString(),
            maxLines = 1,
            color = textColor,
            style = textStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .widthIn(
                    min = textMinWidth,
                ),
        )

        SmallIconButton(
            onClick = { onItemCountChange(menuItemId, +1) },
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add_to_cart),
            color = buttonsColor,
        )
    }
}

@Composable
private fun SmallIconButton(
    onClick: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color = Coffee1706Colors.TextColorLighterLighter
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.size(32.dp),
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = color
        )
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
        )
    }
}
