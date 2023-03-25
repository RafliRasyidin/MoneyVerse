package com.rasyidin.moneyverse.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

@Composable
fun MVButtonPrimary(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    shape: Shape = MaterialTheme.shapes.large,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        onClick = onClick,
        content = content
    )
}